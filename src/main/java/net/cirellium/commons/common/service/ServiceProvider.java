/**
 * Copyright (C) 2022 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 26 2022 17:48:23
 *
 * ServiceProvider.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */

package net.cirellium.commons.common.service;

import java.util.Collection;

import net.cirellium.commons.common.plugin.CirelliumPlugin;

public class ServiceProvider<P extends CirelliumPlugin<P>> {

    private ServiceRegistry<P> serviceRegistry;

    public ServiceProvider(ServiceRegistry<P> serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public AbstractService<P> getService(ServiceType type) {
        AbstractService<P> service = serviceRegistry.getServiceMap().get(type);
        if (service == null) {
            throw new IllegalStateException("Service not registered for type: " + type);
        }
        return service; // ! at this point, the service could be uninitialized
    }

    public Collection<AbstractService<P>> getServices() {
        return serviceRegistry.getServiceMap().values();
    }
}