package net.cirellium.commons.core.exception.service;

public class ServiceStateException extends ServiceException {
    
    public ServiceStateException(String message) {
        super(message);
    }

    public ServiceStateException(String message, Throwable cause) {
        super(message, cause);
    }
}