package org.example.service.impl;

import org.example.entity.ParticipantEntity;
import org.example.entity.SessionEntity;
import org.example.entity.enums.ParticipantRole;
import org.example.entity.enums.ParticipantStatus;
import org.example.exception.AlreadyParticipantException;
import org.example.exception.ServiceException;
import org.example.repository.ParticipantRepository;
import org.example.repository.SessionRepository;
import org.example.repository.impl.ParticipantRepositoryImpl;
import org.example.repository.impl.SessionRepositoryImpl;
import org.example.service.ParticipantService;

import java.util.List;
import java.util.Optional;

public class ParticipantServiceImpl implements ParticipantService {
    private static final SessionRepository sessionRepository = new SessionRepositoryImpl();
    private static final ParticipantRepository participantRepository = new ParticipantRepositoryImpl();

    /**
     * Добавляет участника в сессию.
     * Проверяет, что сессия существует и что участник ещё не добавлен.
     * @param sessionId id сессии
     * @param userId id пользователя
     * @param role роль (например, "OWNER" или "PLAYER")
     * @return объект участника
     */

    @Override
    public ParticipantEntity joinSession(long sessionId, long userId, ParticipantRole role) {

        if (!sessionRepository.isExistsById(sessionId))
            throw new ServiceException("Сессия не найдена");

        if (!isParticipant(sessionId, userId))
            return participantRepository.addParticipant(sessionId, userId, role);
        else
            throw new AlreadyParticipantException("Пользователь уже подключился к сессии");
    }

    @Override
    public boolean leaveSession(long sessionId, long userId) {
        return participantRepository.removeParticipant(sessionId, userId);
    }

    @Override
    public boolean isParticipant(long sessionId, long userId) {
        return participantRepository.getParticipant(sessionId, userId).isPresent();
    }

    @Override
    public List<ParticipantEntity> getParticipants(long sessionId) {
        return participantRepository.getListOfParticipants(sessionId);
    }

    /**
     * Возвращает владельца конкретной сессии.
     * @param sessionId id сессии
     * @return Optional владельца
     */
    public Optional<ParticipantEntity> getOwner(long sessionId) {
        Optional<SessionEntity> session = sessionRepository.findById(sessionId);
        if (session.isEmpty())
            return Optional.empty();
        return participantRepository.getParticipant(sessionId, session.get().getOwnerId());
    }

    /**
     * Обновляет статус участника (например, READY / NOT_READY / FINISHED).
     * @param participantId id участника
     * @param newStatus новый статус
     * @return true, если успешно
     */
    @Override
    public boolean updateParticipantStatus(long sessionId, long participantId, String newStatus) {
        ParticipantStatus status = ParticipantStatus.from(newStatus)
                .orElseThrow(() -> new ServiceException("Неверный статус: " + newStatus));
        return participantRepository.updateStatus(sessionId, participantId, status);
    }

    /**
     * Проверяет, все ли участники готовы (или закончили задание).
     * Используется, чтобы запустить батл или подвести итоги.
     * @param sessionId id сессии
     * @return true, если все готовы
     */
    @Override
    public boolean isParticipantsReady(long sessionId) {
        return participantRepository.countNotSubmitted(sessionId) == 0;
    }
}
