package net.cirellium.commons.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import net.cirellium.commons.core.plugin.CirelliumPlugin;
import net.cirellium.commons.core.service.ServiceHandler;

public abstract class CirelliumBukkitPlugin extends JavaPlugin implements CirelliumPlugin {

    private static CirelliumBukkitPlugin instance;

    private ServiceHandler serviceHandler;
    
    @Override
    public void onLoad() {
        load();
        serviceHandler = new ServiceHandler();
    }

    @Override
    public void onEnable() {
        instance = this;
        serviceHandler.loadServices();

        enable();
    }

    @Override
    public void onDisable() {
        instance = null;

        disable();
    }

    public static CirelliumBukkitPlugin getInstance() {
        return instance;
    }

    public ServiceHandler getServiceHandler() {
        return serviceHandler;
    }

    @Override
    public String getPluginName() {
        return getDescription().getName();
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }

    public abstract void load();

    public abstract void enable();

    public abstract void disable();
    
}