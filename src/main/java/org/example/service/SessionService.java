package org.example.service;


import org.example.dto.request.CreateSessionRequestDto;
import org.example.dto.response.CreateSessionResponseDto;
import org.example.entity.SessionEntity;
import org.example.entity.enums.SessionStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface SessionService {
    CreateSessionResponseDto create(CreateSessionRequestDto dto, Long ownerId);
    Optional<SessionEntity> getSessionById(long sessionId);
    Optional<SessionEntity> getSessionByToken(String token);
    SessionStatus getSessionStatus(Long sessionId);
    void updateStatus(long sessionId, SessionStatus newStatus);
    void startSession(long sessionId);
    void finishSession(long sessionId);
    void deleteSession(long sessionId);
    List<SessionEntity> getSessionsByOwner(long ownerId);
    boolean existsByToken(String token);
    void updateStartTime(long sessionId, Timestamp startTime);
}
