package org.example.exception;

public class InvalidProblemExcecption extends RuntimeException {
    public InvalidProblemExcecption(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidProblemExcecption(String message) {
        super(message);
    }
}
