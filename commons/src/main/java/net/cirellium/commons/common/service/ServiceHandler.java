/**
 * Copyright (C) 2023 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 26 2022 17:48:05
 *
 * ServiceHandler.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package net.cirellium.commons.common.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Logger;

// import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
// import net.cirellium.commons.bukkit.service.AbstractBukkitService;
import net.cirellium.commons.common.exception.service.ServiceDependencyException;
import net.cirellium.commons.common.exception.service.ServiceException;
import net.cirellium.commons.common.logger.CirelliumLogger;
import net.cirellium.commons.common.plugin.CirelliumPlugin;
import net.cirellium.commons.common.util.ExceptionUtils;
import net.cirellium.commons.common.util.clazz.ClassUtils;
import net.cirellium.commons.common.version.Platform; 

/**
 * This class is responsible for loading, initializing, as well as shutting down services.
 * It uses the {@link ServiceRegistry} to store all services, and the {@link ServiceProvider} to provide services.
 * 
 * If any service fails to initialize, a ServiceException will be thrown.
 */
public class ServiceHandler<P extends CirelliumPlugin<P>> {

    protected final CirelliumPlugin<P> plugin;

    // protected @Nullable CirelliumBukkitPlugin<?> bukkitPlugin;

    private final ServiceRegistry<P> registry;
    private final ServiceProvider<P> provider;

    private Logger logger;

    public ServiceHandler(CirelliumPlugin<P> plugin) {
        this.plugin = plugin;
        // if(plugin.getPlatform() == Platform.BUKKIT) this.bukkitPlugin = (CirelliumBukkitPlugin<?>) plugin;
        this.registry = new ServiceRegistry<P>();
        this.provider = new ServiceProvider<P>(registry);
        this.logger = new CirelliumLogger(Platform.getCurrentPlatform(), "ServiceHandler");

        // CompletableFuture.runAsync(() -> loadServices(), plugin.getExecutorService())
                        // .thenRun(() -> initializeServices());
    }

    public ServiceRegistry<P> getRegistry() {
        return registry;
    }

    public ServiceProvider<P> getProvider() {
        return provider;
    }

    @SuppressWarnings( { "rawtypes" })
    public List<AbstractService> loadServices() {
        logger.info("Loading services...");
        List<AbstractService> services = new ArrayList<AbstractService>();
        try {
            for (Class<?> serviceClass : ClassUtils.getAllClasses("net.cirellium", AbstractService.class)) {
                logger.info("Found class " + serviceClass.getSimpleName());

                if (serviceClass.getSimpleName().equals("AbstractBukkitService")) continue;
                if (AbstractService.class.isAssignableFrom(serviceClass)) {
                    if(serviceClass.getSuperclass().getSimpleName().contains("AbstractBukkitService")) {
                        // Constructor<?> constructor = serviceClass.getConstructor(CirelliumBukkitPlugin.class);
                        Constructor<?> constructor = serviceClass.getDeclaredConstructors()[0];

                        logger.info("Found constructor with " + constructor.getParameterCount() + " parameters and " + constructor.getParameterTypes()[0].getSimpleName() + " as first parameter");
    
                        if(Modifier.isAbstract(serviceClass.getModifiers())) {
                            logger.info(serviceClass.getSimpleName() + " class is abstract, loading sub service class...");
    
                            try {
                                Class<?> subServiceClass = ClassUtils.getAllClasses("net.cirellium", serviceClass).iterator().next();
                                logger.info("Found sub service class " + subServiceClass.getSimpleName());
                                if(subServiceClass != null) loadSubService(subServiceClass);
                                continue;
                            } catch (NullPointerException | NoSuchElementException e) {
                                logger.warning("No sub service class found for " + serviceClass.getSimpleName() + ", skipping...");
                                continue;
                            }
                        }
    
                        try {
                            Object service = (AbstractService) constructor.newInstance(plugin);

                            registry.registerService((AbstractService) service);
    
                            logger.info("Loaded service " + serviceClass.getSimpleName());
                        } catch (InvocationTargetException e) {
                            logger.warning("Failed to register service " + serviceClass.getSimpleName() + " because the underlying constructor threw an exception. Skipping...");
                            logger.warning("Reason: " + ExceptionUtils.findOriginalCause(e.getCause()).getMessage());
                            continue;
                        }
                        continue;
                    } 
                    // Create a new instance of the service
                    Constructor<?> constructor = serviceClass.getDeclaredConstructors()[0];

                    logger.info("Found constructor with " + constructor.getParameterCount() + " parameters and " + constructor.getParameterTypes()[0].getSimpleName() + " as first parameter");

                    if(Modifier.isAbstract(serviceClass.getModifiers())) {
                        logger.info(serviceClass.getSimpleName() + " class is abstract, loading sub service class...");

                        try {
                            Class<?> subServiceClass = ClassUtils.getAllClasses("net.cirellium", serviceClass).iterator().next();
                            logger.info("Found sub service class " + subServiceClass.getSimpleName());
                            if(subServiceClass != null) loadSubService(subServiceClass);
                            continue;
                        } catch (NullPointerException | NoSuchElementException e) {
                            logger.warning("No sub service class found for " + serviceClass.getSimpleName() + ", skipping...");
                            continue;
                        }
                    }

                    // Object service = (AbstractService) constructor.newInstance(plugin);

                    // services.add((AbstractService) serviceClass.newInstance());
                    // registry.registerService((AbstractService) service);

                    // plugin.getLogger().info("Loaded service " + serviceClass.getSimpleName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services;
    }

    public void loadService(Class<?> serviceClass) {

    }

    public void loadSubService(Class<?> subServiceClass) {
        Constructor<?> constructor = subServiceClass.getDeclaredConstructors()[0];

        logger.info("Loading sub-service class " + subServiceClass.getSimpleName());
        logger.info("Found constructor with " + constructor.getParameterCount() + " parameters and " + constructor.getParameterTypes()[0].getSimpleName() + " as first parameter");

        try {
            constructor.newInstance(plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
    public void initializeServices() throws ServiceException {
        logger.info("Initializing services...");

        Set<ServiceType> initializedServices = new HashSet<>();
        Set<AbstractService<P>> toInitialize = new HashSet<>(registry.getServiceMap().values());

        // Initialize all services in respect to their dependencies
        while (!toInitialize.isEmpty()) {
            boolean initialized = false;
            for (AbstractService<P> service : toInitialize) {
                logger.info("Checking service " + service.getName() + " for dependencies...");
                // Check if the service has all dependencies initialized
                if (service.getDependencies() == null || initializedServices.containsAll(service.getDependencies())) {
                    // Initialize the service
                    logger.info("Attempting to prepare service " + service.getName());
                    service.prepare();
                    initializedServices.add(service.getServiceType());
                    toInitialize.remove(service);
                    initialized = true;
                } else {
                    // Check if the service has any missing dependencies
                    Set<ServiceType> missingDependencies = new HashSet<>(service.getDependencies());
                    missingDependencies.stream().filter(type -> !initializedServices.contains(type)).forEach(dependencyType -> {
                        try {
                            logger.info("Attempting to prepare dependency " + dependencyType.getName());
                            service.getService(dependencyType).prepare();
                            initializedServices.add(dependencyType);
                            toInitialize.remove(service.getService(dependencyType));
                        } catch (ServiceDependencyException e) {
                            e.printStackTrace();
                        }
                    });
                    service.prepare();
                    initializedServices.add(service.getServiceType());
                    toInitialize.remove(service);
                    initialized = true;
                }
            }
            if (!initialized) {
                throw new IllegalStateException("Failed to initialize services, circular dependency detected");
            }
        }
    }

    public void shutdownServices() {
        for (AbstractService<P> service : registry.getServiceMap().values()) {
            service.shutdown();
            
            registry.unregisterService(service.getServiceType());
        }
    }
}