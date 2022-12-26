/*
 * Copyright (C) 2022 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 26 2022 18:50:10
 *
 * ServiceDependencyException.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package net.cirellium.commons.core.exception.service;

import java.util.Set;

import net.cirellium.commons.core.service.ServiceType;

public class ServiceDependencyException extends ServiceException {

    private final Set<ServiceType> missingDependencies;

    public ServiceDependencyException(String message, Set<ServiceType> dependencies) {
        super(message);

        this.missingDependencies = dependencies;
    }

    public ServiceDependencyException(String message, Throwable cause, Set<ServiceType> dependencies) {
        super(message, cause);

        this.missingDependencies = dependencies;
    }

    public Set<ServiceType> getMissingDependencies() {
        return missingDependencies;
    }
    
}