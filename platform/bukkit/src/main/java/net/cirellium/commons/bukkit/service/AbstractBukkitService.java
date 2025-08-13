/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sat Jan 21 2023 22:43:35
*
* AbstractBukkitService.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.service;

import org.bukkit.scheduler.BukkitScheduler;

import lombok.Getter;
import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.common.service.AbstractService;
import net.cirellium.commons.common.service.ServiceType;

/**
 * A wrapper for an {@link AbstractService} that contains an instance of the {@link CirelliumBukkitPlugin} and provides Bukkit-specific methods.
 */
public abstract class AbstractBukkitService extends AbstractService<CirelliumBukkitPlugin> {

    @Getter
    protected final CirelliumBukkitPlugin bukkitPlugin;

    public AbstractBukkitService(CirelliumBukkitPlugin plugin) {
        super(plugin);
        this.bukkitPlugin = (CirelliumBukkitPlugin) plugin.getPlugin();
    }

    public AbstractBukkitService(CirelliumBukkitPlugin plugin, ServiceType file) {
        super(plugin, file);
        this.bukkitPlugin = (CirelliumBukkitPlugin) plugin.getPlugin();
    }

    public AbstractBukkitService(CirelliumBukkitPlugin plugin, ServiceType type, ServiceType... dependencies) {
        super(plugin, type, true, dependencies);
        this.bukkitPlugin = (CirelliumBukkitPlugin) plugin.getPlugin();
    }

    public AbstractBukkitService(CirelliumBukkitPlugin plugin, ServiceType type, boolean autoInitialize, ServiceType... dependencies) {
        super(plugin, type, autoInitialize, dependencies);
        this.bukkitPlugin = (CirelliumBukkitPlugin) plugin.getPlugin();
    }

    @Override
    public abstract void initialize(CirelliumBukkitPlugin plugin);

    @Override
    public abstract void shutdown(CirelliumBukkitPlugin plugin);

    public BukkitScheduler getScheduler() {
        return bukkitPlugin.getServer().getScheduler();
    }
}