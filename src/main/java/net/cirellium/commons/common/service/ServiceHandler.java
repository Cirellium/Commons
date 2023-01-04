/**
 * Copyright (C) 2022 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 26 2022 17:48:05
 *
 * ServiceHandler.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package net.cirellium.commons.common.service;

import java.util.HashSet;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;

import net.cirellium.commons.common.exception.service.ServiceDependencyException;
import net.cirellium.commons.common.exception.service.ServiceException;
import net.cirellium.commons.common.exception.service.ServiceNotFoundException;
import net.cirellium.commons.common.exception.service.ServiceStateException;
import net.cirellium.commons.common.plugin.CirelliumPlugin; 

/**
 * This class is responsible for loading, initializing, as well as shutting down services.
 * It uses the {@link ServiceRegistry} to store all services, and the {@link ServiceProvider} to provide services.
 * 
 * If any service fails to initialize, a ServiceException will be thrown.
 */
public class ServiceHandler<P extends CirelliumPlugin<P>> {

    private final ServiceRegistry<P> registry;
    private final ServiceProvider<P> provider;

    public ServiceHandler() {
        this.registry = new ServiceRegistry<P>();
        this.provider = new ServiceProvider<P>(registry);
    }

    public ServiceRegistry<P> getRegistry() {
        return registry;
    }

    public ServiceProvider<P> getProvider() {
        return provider;
    }

    public void loadServices() throws ServiceException {
        try {
            // Load the service classes using the ServiceLoader
            ServiceLoader<AbstractService> serviceLoader = ServiceLoader.load(AbstractService.class, getClass().getClassLoader());
            for (AbstractService service : serviceLoader) {
                // Register the service with the ServiceRegistry
                registry.registerService(service);
            }
        } catch (ServiceConfigurationError e) {
            throw new ServiceException("Error loading services", e);
        }
    }
  
    public void initializeServices() throws ServiceException {
        Set<AbstractService<P>> initializedServices = new HashSet<>();
        boolean initialized = false;
        while (initializedServices.size() < registry.getServiceMap().size()) {
            for (AbstractService<P> service : registry.getServiceMap().values()) {
                initialized = false;
                if (initializedServices.contains(service) || !service.autoInitialize()) {
                    continue;
                }
                try {
                    service.prepare();
                    initializedServices.add(service);
                    initialized = true;
                    break;
                } catch (ServiceException e) {
                    if (e instanceof ServiceStateException) {
                        continue;
                    }
                    if (e instanceof ServiceDependencyException) {
                        Set<ServiceType> missingDependencies = ((ServiceDependencyException) e).getMissingDependencies();

                        for (ServiceType dependency : missingDependencies) {
                            AbstractService<P> dependencyService = registry.getServiceMap().get(dependency);
                            if (dependencyService == null) {
                                throw new ServiceNotFoundException(dependencyService, "Dependency service not found for type: " + dependency);
                            }
                            if (!initializedServices.contains(dependencyService)) {
                                dependencyService.prepare();
                            }
                        }
                    }
                }
            }
            
        }
        if (!initialized) {
            throw new ServiceStateException(null, "Not all services are initialized");
        }
    }

    public void shutdownServices() {
        for (AbstractService<P> service : registry.getServiceMap().values()) {
            service.shutdown();
            
            registry.unregisterService(service.getType());
        }
    }
}