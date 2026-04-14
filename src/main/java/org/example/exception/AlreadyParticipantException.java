package org.example.exception;

public class AlreadyParticipantException extends RuntimeException {
    public AlreadyParticipantException(String message, Throwable cause) {
        super(message, cause);
    }
    public AlreadyParticipantException(String message) {
        super(message);
    }
}
