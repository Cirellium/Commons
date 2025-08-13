package net.cirellium.commons.common.exception.service;

import net.cirellium.commons.common.service.AbstractService;

public class ServiceException extends RuntimeException {

    protected AbstractService<?> service;

    public ServiceException(AbstractService<?> service, String message) {
        super(message);

        this.service = service;
    }
  
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractService<?> getService() {
        return service;
    }
}