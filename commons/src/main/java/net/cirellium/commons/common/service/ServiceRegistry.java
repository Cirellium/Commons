/**
 * Copyright (C) 2023 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 26 2022 17:48:32
 *
 * ServiceRegistry.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */

package net.cirellium.commons.common.service;

import java.util.EnumMap;
import java.util.Map;

import net.cirellium.commons.common.plugin.CirelliumPlugin;

/**
 * This class is responsible for registering and storing all services.
 * It uses the {@link ServiceType} enum to map services to their type.
 */
public class ServiceRegistry<P extends CirelliumPlugin<P>> {

    private final Map<ServiceType, AbstractService<P>> serviceMap = new EnumMap<>(ServiceType.class);

    protected ServiceRegistry() {
        
    }

    public Map<ServiceType, AbstractService<P>> getServiceMap() {
        return serviceMap;
    }

    @SuppressWarnings("unchecked")
    public void registerService(AbstractService<?> service) {
        ServiceType type = service.getServiceType();
        serviceMap.putIfAbsent(type, (AbstractService<P>) service);
    }
  
    public void unregisterService(ServiceType type) {
        serviceMap.remove(type);
    }
}