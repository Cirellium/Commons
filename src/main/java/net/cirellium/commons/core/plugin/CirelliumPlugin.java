/*
 * Copyright (C) 2022 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 21 2022 10:18:45
 *
 * CirelliumPlugin.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package net.cirellium.commons.core.plugin;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import net.cirellium.commons.core.service.Service;
import net.cirellium.commons.core.service.ServiceHandler;
import net.cirellium.commons.core.service.ServiceType;
import net.cirellium.commons.core.version.Platform;

public interface CirelliumPlugin {
    
    public String getPluginName();

    public String getVersion();

    public File getDataFolder();

    public ServiceHandler getServiceHandler();

    public Platform getPlatform();

    default Optional<Service> getService(ServiceType type) {
        return getServices().stream().filter(service -> service.getType() == type).findFirst();
    }

    default Collection<Service> getServices() {
        return getServiceHandler().getProvider().getServices();
    }
    // ! Other methods:
    // ! Platform getPlatform();
    // ! Logger getLogger();
    // Other methods for all plugins
    // ! ArrayList<Manager> getManagers();
    // ! CommandCache getCommandCache();
    // ! ConfigCache getConfigCache();
    // Dependencies, files etc

}