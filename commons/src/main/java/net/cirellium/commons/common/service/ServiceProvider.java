/**
 * Copyright (C) 2023 Cirellium Network - All Rights Reserved
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

    public <S extends AbstractService<?>> S findService(Class<S> clazz) {
        return clazz.cast(this.serviceRegistry.getServiceMap().values().stream()
                .filter(controller -> controller.getClass().equals(clazz))
                .findFirst().orElse(null));

        // return serviceRegistry.getServiceMap().values().stream()
        // // .filter(serviceClass::isInstance)
        // .filter(service -> service.getClass().isAssignableFrom(genericTypeClass))
        // .map(genericTypeClass::cast)
        // .findFirst()
        // .orElse(null);
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