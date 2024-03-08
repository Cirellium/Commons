package net.cirellium.commons.common.exception;

public class ShutdownException extends RuntimeException {

    public ShutdownException(String message) {
        super(message);
    }

    public ShutdownException(String message, Throwable cause) {
        super(message, cause);
    }
}