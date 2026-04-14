package org.example.exception;

public class AddParticipantException extends RuntimeException {
  public AddParticipantException(String message, Throwable cause) {
    super(message, cause);
  }
  public AddParticipantException(String message) {
    super(message);
  }
}
