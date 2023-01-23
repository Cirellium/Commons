package net.cirellium.commons.common.exception.service;

import net.cirellium.commons.common.service.AbstractService;

public class ServiceStateException extends ServiceException {
    
    public ServiceStateException(AbstractService<?> service, String message) {
        super(service, message);
    }

    public ServiceStateException(String message, Throwable cause) {
        super(message, cause);
    }
}