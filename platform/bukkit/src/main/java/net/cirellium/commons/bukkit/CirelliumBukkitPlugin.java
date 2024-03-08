/**
 * Copyright (C) 2023 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Tue Dec 21 2022 09:20:48
 *
 * CirelliumBukkitPlugin.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package net.cirellium.commons.bukkit;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import net.cirellium.commons.common.logger.CirelliumLogger;
import net.cirellium.commons.common.plugin.CirelliumPlugin;
import net.cirellium.commons.common.service.ServiceHandler;
import net.cirellium.commons.common.service.ServiceHolder;
import net.cirellium.commons.common.version.Platform;
import net.cirellium.commons.common.version.Version;

/**
 * This class represents the base class for all Cirellium Bukkit plugins.
 */
public abstract class CirelliumBukkitPlugin extends JavaPlugin implements CirelliumPlugin<CirelliumBukkitPlugin>, ServiceHolder<CirelliumBukkitPlugin> {

    protected CirelliumBukkitPlugin plugin;

    private ServiceHandler<CirelliumBukkitPlugin> serviceHandler;

    @Getter
    private ExecutorService executorService;
    
    @Override
    public void onLoad() {
        plugin = this;
        executorService = Executors.newFixedThreadPool(4);
        boolean load = false;

        try {
            load = load();
        } catch (RuntimeException e) {
            load = false;
            getLogger().severe("An exception occurred while loading the plugin!");
            e.printStackTrace();
        }

        if (!load) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        serviceHandler = new ServiceHandler<CirelliumBukkitPlugin>(plugin);
        serviceHandler.loadServices();
    }

    @Override
    public void onEnable() {
        serviceHandler.initializeServices();

        // ! First, load all services, then run the custom enable method
        enable();
    }

    @Override
    public void onDisable() {
        serviceHandler.shutdownServices();

        disable();
    }

    // public abstract P getSelf();

    @Override
    public String getPluginName() {
        return getDescription().getName();
    }

    @Override
    public Version getVersion() {
        return new Version(getDescription().getVersion());
    }

    @Override
    public ServiceHandler<CirelliumBukkitPlugin> getServiceHandler() {
        return serviceHandler;
    }

    @Override
    public ServiceHolder<CirelliumBukkitPlugin> getServiceHolder() {
        return this;
    }

    @Override
    public Platform getPlatform() {
        return Platform.BUKKIT;
    }

    @Override
    public CirelliumLogger getLogger() {
        return new CirelliumLogger(getPlatform(), getPluginName().toLowerCase());
    }

    public abstract boolean load();

    public abstract void enable();

    public abstract void disable();
    
}