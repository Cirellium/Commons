/**
 * Copyright (C) 2022 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 21 2022 10:21:03
 *
 * Service.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package net.cirellium.commons.common.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import net.cirellium.commons.common.exception.service.ServiceDependencyException;
import net.cirellium.commons.common.exception.service.ServiceNotFoundException;
import net.cirellium.commons.common.exception.service.ServiceStateException;
import net.cirellium.commons.common.plugin.CirelliumPlugin;

public abstract class AbstractService<P extends CirelliumPlugin<P>> {
    
    protected final P plugin;

    protected final Logger logger;

    protected final ServiceType type;

    protected final Set<ServiceType> dependencies;

    protected boolean initialized = false, autoInitialize = false;

    public AbstractService(P plugin) {
        this(plugin, ServiceType.OTHER, true);
    }

    public AbstractService(P plugin, boolean autoInitialize) {
        this(plugin, ServiceType.OTHER, autoInitialize);
    }

    public AbstractService(P plugin, ServiceType type, ServiceType... dependencies) {
        this(plugin, type, true, dependencies);
    }

    public AbstractService(P plugin, ServiceType type, boolean autoInitialize, ServiceType... dependencies) {
        this.plugin = plugin;
        this.type = type;
        this.logger = plugin.getLogger();

        this.autoInitialize = autoInitialize;

        this.dependencies = new HashSet<>(Arrays.asList(dependencies));
        //ServiceRegistry.getInstance().registerService(this);
        //prepare();
    }

    public void prepare() {
        if(initialized) {
            throw new ServiceStateException(this, "Service already initialized");
        }

        Set<ServiceType> missingDependencies = new HashSet<>();

        for (ServiceType dependency : dependencies) {
            AbstractService<?> service = plugin.getServiceHolder().getService(dependency);
            if (!service.isInitialized()) {
                missingDependencies.add(dependency);
            }
        }
        if(missingDependencies.size() > 0) throw new ServiceDependencyException(this, "Dependencies not initialized", missingDependencies);
        
        initialize();
        initialized = true;
    }

    public final boolean isInitialized() {
        return initialized && plugin.getServiceHolder().getServices().contains(this);
    }

    public final boolean autoInitialize() {
        return autoInitialize;
    }

    public final CirelliumPlugin<P> getPlugin() { return plugin; }

    public final AbstractService<?> getService(ServiceType type) {
        return plugin.getServiceHolder().getServices()
            .stream()
            .filter(service -> service.getType() == type)
            .findFirst()
            .orElseThrow(() -> new ServiceNotFoundException(null, "Service not found for type: " + type));
    }

    public final ServiceType getType() { return type; }

    public abstract void initialize();
    public abstract void shutdown();

}