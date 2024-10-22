/**
 * Copyright (C) 2023 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 21 2022 10:21:03
 *
 * Service.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package net.cirellium.commons.common.service;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.reflect.TypeToken;

import net.cirellium.commons.common.exception.service.ServiceDependencyException;
import net.cirellium.commons.common.exception.service.ServiceNotFoundException;
import net.cirellium.commons.common.exception.service.ServiceStateException;
import net.cirellium.commons.common.logger.SimpleCirelliumLogger;
import net.cirellium.commons.common.plugin.CirelliumPlugin;
import net.cirellium.commons.common.util.Lifecycle;

public abstract class AbstractService<C extends CirelliumPlugin<C>> implements Lifecycle<C> {

    protected final C plugin;

    protected final Logger logger;

    protected final ServiceType serviceType;

    protected Set<ServiceType> dependencies;

    protected final TypeToken<C> typeToken = new TypeToken<C>(getClass()) {};

    protected final Type type = typeToken.getType(); // or getRawType() to return Class<? super T>

    protected boolean initialized = false, autoInitialize = false;

    public AbstractService(C plugin) {
        this(plugin, ServiceType.NONE, true);
    }

    public AbstractService(C plugin, boolean autoInitialize) {
        this(plugin, ServiceType.NONE, autoInitialize);
    }

    public AbstractService(C plugin, ServiceType type, ServiceType... dependencies) {
        this(plugin, type, true, dependencies);
    }

    public AbstractService(C plugin, ServiceType type, boolean autoInitialize, ServiceType... dependencies) {
        this.plugin = plugin;
        this.serviceType = type;
        this.logger = new SimpleCirelliumLogger(plugin.getPlatform(), getName());

        this.autoInitialize = autoInitialize;

        if (dependencies[0] != ServiceType.NONE)
            this.dependencies = new HashSet<ServiceType>(Arrays.asList(dependencies));

        logger.info(getName() + " has dependencies: " + String.valueOf(dependencies[0] != ServiceType.NONE));

        // plugin.getServiceHolder().getServiceRegistry().registerService(this);
        // prepare();
    }

    public void prepare() {
        if (initialized) {
            throw new ServiceStateException(this, "Service already initialized");
        }

        if (!hasDependencies()) {
            initialize(plugin);
            logger.fine("Service successfully initialized");
            initialized = true;
            return;
        }

        Set<ServiceType> missingDependencies = new HashSet<>();

        for (ServiceType dependency : (dependencies == null ? new HashSet<ServiceType>() : dependencies)) {
            AbstractService<?> service = plugin.getServiceHolder().getService(dependency);
            if (!service.isInitialized()) {
                missingDependencies.add(dependency);
            }
        }
        if (missingDependencies.size() > 0)
            throw new ServiceDependencyException(this, "Dependencies not initialized for " + getName(),
                    missingDependencies);

        initialize(plugin);
        logger.fine("Service successfully initialized");
        initialized = true;
    }

    public String getName() {
        return serviceType.getClass().getSimpleName();
    }

    public final boolean isInitialized() {
        return initialized && plugin.getServiceHolder().getServiceProvider().getServices().contains(this);
    }

    public final boolean autoInitialize() {
        return autoInitialize;
    }

    public final CirelliumPlugin<?> getPlugin() {
        return plugin;
    }

    public final boolean hasDependencies() {
        return dependencies != null;
    }

    public final Set<ServiceType> getDependencies() {
        return dependencies;
    }

    public final AbstractService<?> getService(ServiceType type) {
        return plugin.getServiceHolder().getServiceProvider().getServices()
                .stream()
                .filter(service -> service.getServiceType() == type)
                .findFirst()
                .orElseThrow(() -> new ServiceNotFoundException(null, "Service not found for type: " + type));
    }

    public final ServiceType getServiceType() {
        return serviceType;
    }

    public abstract void initialize(C plugin);

    public abstract void shutdown(C plugin);

    public abstract Logger getLogger();

}