package org.example.service;

import org.example.entity.ParticipantEntity;
import org.example.entity.enums.ParticipantRole;

import java.util.List;
import java.util.Optional;

public interface ParticipantService {
    ParticipantEntity joinSession(long sessionId, long userId, ParticipantRole role);
    boolean leaveSession(long sessionId, long userId);
    boolean isParticipant(long sessionId, long userId);
    List<ParticipantEntity> getParticipants(long sessionId);
    Optional<ParticipantEntity> getOwner(long sessionId);
    boolean updateParticipantStatus(long sessionId, long participantId, String newStatus);
    boolean isParticipantsReady(long sessionId);
}
