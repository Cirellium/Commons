package net.cirellium.commons.common.exception.service;

import net.cirellium.commons.common.service.AbstractService;

public class ServiceNotFoundException extends ServiceException {
    
    public ServiceNotFoundException(AbstractService<?> service, String message) {
        super(service, message);
    }

    public ServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    } 
}