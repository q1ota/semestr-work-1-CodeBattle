package org.example.service.impl;

import org.example.dto.request.CreateSessionRequestDto;
import org.example.dto.response.CreateSessionResponseDto;
import org.example.entity.SessionEntity;
import org.example.entity.enums.ParticipantRole;
import org.example.entity.enums.SessionStatus;
import org.example.exception.ServiceException;
import org.example.repository.impl.ParticipantRepositoryImpl;
import org.example.repository.impl.SessionRepositoryImpl;
import org.example.service.SessionService;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


public class SessionServiceImpl implements SessionService {
    private static final SessionRepositoryImpl sessionRepository = new SessionRepositoryImpl();
    private static final ParticipantRepositoryImpl participantRepository = new ParticipantRepositoryImpl();

    @Override
    public CreateSessionResponseDto create(CreateSessionRequestDto dto, Long ownerId) {

        if (ownerId == null || ownerId <= 0) {
            throw new ServiceException("Пользователь не авторизован");
        }
        if (dto == null) {
            throw new ServiceException("Ошибка с получением данных с сервера");
        }

        SessionEntity session = SessionEntity.builder()
                .taskId(dto.getTaskId())
                .ownerId(ownerId)
                .inviteToken(generateUniqueToken())
                .status(SessionStatus.LOBBY)
                .startTime(null)
                .durationSeconds(dto.getDuration())
                // created_at
                .build();

        long sessionId = sessionRepository.save(session);
        participantRepository.addParticipant(sessionId, ownerId, ParticipantRole.OWNER);
        return new CreateSessionResponseDto(session.getId(), session.getInviteToken());
    }


    @Override
    public Optional<SessionEntity> getSessionById(long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    @Override
    public Optional<SessionEntity> getSessionByToken(String token) {
        return sessionRepository.findByInviteToken(token);
    }

    @Override
    public void updateStatus(long sessionId, SessionStatus newStatus) {
        sessionRepository.updateStatus(sessionId, newStatus);

    }

    @Override
    public void startSession(long sessionId) {
        sessionRepository.updateStartTime(sessionId, Timestamp.from(Instant.now()));
        sessionRepository.updateStatus(sessionId, SessionStatus.RUNNING);
    }

    @Override
    public void finishSession(long sessionId) {
        sessionRepository.updateStatus(sessionId, SessionStatus.FINISHED);
    }

    // прямо из БД? хорошо ли это?
    @Override
    public void deleteSession(long sessionId) {
        sessionRepository.delete(sessionId);
    }

    @Override
    public List<SessionEntity> getSessionsByOwner(long ownerId) {
        return sessionRepository.listByOwner(ownerId);
    }

    @Override
    public SessionStatus getSessionStatus(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .map(SessionEntity::getStatus)
                .orElse(SessionStatus.LOBBY);
    }

    @Override
    public boolean existsByToken(String token) {
        return sessionRepository.isExistByToken(token);
    }

    // убрал boolean - нужно ли?
    @Override
    public void updateStartTime(long sessionId, Timestamp startTime) {
        sessionRepository.updateStartTime(sessionId, startTime);
    }

    public String generateUniqueToken() {
        final String alphabetAndNums = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final SecureRandom random = new SecureRandom();

        String token = randomToken(alphabetAndNums, random);
        while (sessionRepository.isExistByToken(token)) {
            token = randomToken(alphabetAndNums, random);
        }
        return token;
    }

    private String randomToken(String chars, SecureRandom random) {
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

}
