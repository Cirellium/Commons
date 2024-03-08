/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Wed Jan 04 2023 08:49:36
*
* ServiceHolder.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.service;

import java.util.Collection;
import java.util.Optional;

import net.cirellium.commons.common.plugin.CirelliumPlugin;
import net.cirellium.commons.common.util.SimpleProvider;

/**
 * This interface provides methods to get services from the service handler.
 * It extends the {@link SimpleProvider} interface, as it also serves as a provider for the service handler.
 * 
 * @author Fear
 */
public interface ServiceHolder<P extends CirelliumPlugin<P>> extends SimpleProvider<ServiceHandler<P>> {
    
    /**
     * Returns the service handler.
     * @return The service handler.
     */
    public ServiceHandler<P> getServiceHandler();
    
    /**
     * Provides the service handler by calling the {@link #getServiceHandler()} method.
     * @return The service handler.
     */
    @Override
    default ServiceHandler<P> provide() {
        return getServiceHandler();
    }

    /**
     * Returns the service registry.
     * @return The service registry.
     */
    public default ServiceRegistry<P> getServiceRegistry() {
        return getServiceHandler().getRegistry();
    }

    /**
     * Returns the service provider.
     * @return The service provider.
     */
    public default ServiceProvider<P> getServiceProvider() {
        return getServiceHandler().getProvider();
    }

    /**
     * Returns the service with the given type.
     * @param type The type of the service.
     * @return The service.
     */
    default AbstractService<P> getService(ServiceType type) {
        return getServiceHandler().getProvider().getService(type);
    }

    /**
     * Returns an optional of the service with the given type.
     * @param type The type of the service.
     * @return An optional of the service.
     */
    default Optional<AbstractService<P>> getOptionalService(ServiceType type) {
        return getServices().stream().filter(service -> service.getServiceType() == type).findFirst();
    }

    /**
     * Returns a collection of all registered services.
     * @return A collection of all registered services.
     */
    @Deprecated(forRemoval = true)
    default Collection<AbstractService<P>> getServices() {
        return getServiceHandler().getProvider().getServices();
    }
}