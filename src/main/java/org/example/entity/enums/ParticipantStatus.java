package org.example.entity.enums;

import java.util.Optional;

public enum ParticipantStatus {
    JOINED,
    SUBMITTED,
    DISCONNECTED,
    FINISHED;

    public static Optional<ParticipantStatus> from(String name) {
        if (name == null)
            return Optional.empty();
        try {
            return Optional.of(ParticipantStatus.valueOf(name.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
