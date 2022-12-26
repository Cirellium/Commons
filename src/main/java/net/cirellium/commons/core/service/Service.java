/*
 * Copyright (C) 2022 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 21 2022 10:21:03
 *
 * Service.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package net.cirellium.commons.core.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.cirellium.commons.core.exception.service.ServiceDependencyException;
import net.cirellium.commons.core.exception.service.ServiceNotFoundException;
import net.cirellium.commons.core.exception.service.ServiceStateException;
import net.cirellium.commons.core.plugin.CirelliumPlugin;

public abstract class Service<P extends CirelliumPlugin<P>> {
    
    private final P plugin;

    private final ServiceType type;

    private final Set<ServiceType> dependencies;

    private boolean initialized = false;

    public Service(P plugin, ServiceType type, ServiceType... dependencies) {
        this.plugin = plugin;
        this.type = type;

        this.dependencies = new HashSet<>(Arrays.asList(dependencies));
        //ServiceRegistry.getInstance().registerService(this);
        prepare();
    }

    public void prepare() {
        if(initialized) {
            throw new ServiceStateException("Service already initialized");
        }

        Set<ServiceType> missingDependencies = new HashSet<>();

        for (ServiceType dependency : dependencies) {
            Service<P> service = plugin.getService(dependency).get();
            if (!service.isInitialized()) {
                missingDependencies.add(dependency);
            }
        }
        if(missingDependencies.size() > 0) throw new ServiceDependencyException("Dependencies not initialized", missingDependencies);
        
        initialize();
        initialized = true;
    }

    public final boolean isInitialized() {
        return initialized && plugin.getServices().contains(this);
    }

    public final CirelliumPlugin<P> getPlugin() { return plugin; }

    public final Service<P> getService(ServiceType type) {
        return plugin.getServices()
            .stream()
            .filter(service -> service.getType() == type)
            .findFirst()
            .orElseThrow(() -> new ServiceNotFoundException("Service not found for type: " + type));
    }

    public final ServiceType getType() { return type; }

    public abstract void initialize();
    public abstract void shutdown();

}