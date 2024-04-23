/**
 * Copyright (C) 2023 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 21 2022 10:18:45
 *
 * CirelliumPlugin.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package net.cirellium.commons.common.plugin;

import java.io.File;
import java.util.concurrent.ExecutorService;

import net.cirellium.commons.common.logger.CirelliumLogger;
import net.cirellium.commons.common.service.ServiceHolder;
import net.cirellium.commons.common.version.Platform;
import net.cirellium.commons.common.version.Version;

/**
 * This interface represents a plugin running on Cirellium Network.
 * 
 * @author Fear
 * @param <P> The type of the plugin
 */
public interface CirelliumPlugin<P> {
    
    public String getPluginName();

    public Version getVersion();

    public File getDataFolder();

    public Platform getPlatform();

    public ServiceHolder<? super P> getServiceHolder();

    public CirelliumLogger getLogger();

    public ExecutorService getExecutorService();

    public MessageProvider<?> getMessageProvider();

    public P getPlugin();
    
    // ! Other methods:
    // ! Platform getPlatform();
    // ! Logger getLogger();
    // Other methods for all plugins
    // ! ArrayList<Manager> getManagers();
    // ! CommandCache getCommandCache();
    // ! ConfigCache getConfigCache();
    // Dependencies, files etc

}