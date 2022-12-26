/*
 * Copyright (C) 2022 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 26 2022 17:48:05
 *
 * ServiceHandler.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package net.cirellium.commons.core.service;

import java.util.HashSet;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;

import net.cirellium.commons.core.exception.service.ServiceDependencyException;
import net.cirellium.commons.core.exception.service.ServiceException;
import net.cirellium.commons.core.exception.service.ServiceNotFoundException;
import net.cirellium.commons.core.exception.service.ServiceStateException;

public class ServiceHandler {

    private final ServiceRegistry registry;
    private final ServiceProvider provider;

    public ServiceHandler() {
        this.registry = new ServiceRegistry();
        this.provider = new ServiceProvider(registry);
    }

    public ServiceRegistry getRegistry() {
        return registry;
    }

    public ServiceProvider getProvider() {
        return provider;
    }

    public void loadServices() throws ServiceException {
        try {
            // Load the service classes using the ServiceLoader
            ServiceLoader<Service> serviceLoader = ServiceLoader.load(Service.class, getClass().getClassLoader());
            for (Service service : serviceLoader) {
                // Register the service with the ServiceRegistry
                registry.registerService(service);
            }
        } catch (ServiceConfigurationError e) {
            throw new ServiceException("Error loading services", e);
        }
    }
  
    public void initializeServices() throws ServiceException {
        Set<Service> initializedServices = new HashSet<>();
        while (initializedServices.size() < registry.getServiceMap().size()) {
            boolean initialized = false;
            for (Service service : registry.getServiceMap().values()) {
                if (initializedServices.contains(service)) {
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
                            Service dependencyService = registry.getServiceMap().get(dependency);
                            if (dependencyService == null) {
                                throw new ServiceNotFoundException("Dependency not found: " + dependency);
                            }
                            if (!initializedServices.contains(dependencyService)) {
                                dependencyService.prepare();
                            }
                        }
                    }
                }
            }
            if (!initialized) {
                throw new ServiceStateException("Circular dependency detected");
            }
        }
    }

    public void shutdownServices() {
        for (Service service : registry.getServiceMap().values()) {
            service.shutdown();
            registry.unregisterService(service.getType());
        }
    }
}