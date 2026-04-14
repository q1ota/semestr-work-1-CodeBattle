package org.example.entity.enums;

import java.util.Optional;

public enum SessionStatus {
    LOBBY,
    RUNNING,
    FINISHED;

    public static Optional<SessionStatus> from(String name) {
        if (name == null)
            return Optional.empty();
        try {
            return Optional.of(SessionStatus.valueOf(name.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}

