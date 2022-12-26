/*
 * Copyright (C) 2022 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 26 2022 17:48:23
 *
 * ServiceProvider.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */

package net.cirellium.commons.core.service;

import java.util.Collection;

public class ServiceProvider {

    private ServiceRegistry serviceRegistry;

    public ServiceProvider(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public Service getService(ServiceType type) {
        Service service = serviceRegistry.getServiceMap().get(type);
        if (service == null) {
            throw new IllegalStateException("Service not registered for type: " + type);
        }
        return service;
    }

    public Collection<Service> getServices() {
        return serviceRegistry.getServiceMap().values();
    }
}