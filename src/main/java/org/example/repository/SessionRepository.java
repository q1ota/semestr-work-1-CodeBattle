package org.example.repository;

import org.example.entity.SessionEntity;
import org.example.entity.enums.SessionStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    long save(SessionEntity session);
    Optional<SessionEntity> findById(long id);
    Optional<SessionEntity> findByInviteToken(String token);
    List<SessionEntity> listActive();
    List<SessionEntity> listByOwner(long ownerId);
    void updateStatus(long sessionId, SessionStatus status);
    void updateStartTime(long sessionId, Timestamp startTime);
    boolean isExistsById(long id);
    boolean isExistByToken(String token);
    void delete(long sessionId);
}