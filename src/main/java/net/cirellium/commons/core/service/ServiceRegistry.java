/*
 * Copyright (C) 2022 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 26 2022 17:48:32
 *
 * ServiceRegistry.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */

package net.cirellium.commons.core.service;

import java.util.EnumMap;
import java.util.Map;

public class ServiceRegistry {

    private final Map<ServiceType, Service> serviceMap = new EnumMap<>(ServiceType.class);

    protected ServiceRegistry() {
        
    }

    public Map<ServiceType, Service> getServiceMap() {
        return serviceMap;
    }

    public void registerService(Service service) {
        ServiceType type = service.getType();
        if (serviceMap.containsKey(type)) {
            throw new IllegalStateException("Service already registered for type: " + type);
        }
        serviceMap.put(type, service);
    }
  
    public void unregisterService(ServiceType type) {
        serviceMap.remove(type);
    }
}