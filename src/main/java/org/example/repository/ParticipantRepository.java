package org.example.repository;

import org.example.entity.ParticipantEntity;
import org.example.entity.enums.ParticipantRole;
import org.example.entity.enums.ParticipantStatus;

import java.util.List;
import java.util.Optional;


public interface ParticipantRepository {
    ParticipantEntity addParticipant(long sessionId, long userId, ParticipantRole role);
    boolean removeParticipant(long sessionId, long userId);
    List<ParticipantEntity> getListOfParticipants(long sessionId);
    Optional<ParticipantEntity> getParticipant(long sessionId, long userId);
    boolean updateStatus(long sessionId, long userId, ParticipantStatus status);
    boolean isParticipantSubmitted(long sessionId, long userId);
    int countNotSubmitted(long sessionId);
    int countParticipants(long sessionId);
}
