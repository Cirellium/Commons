package net.cirellium.commons.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import net.cirellium.commons.core.plugin.CirelliumPlugin;
import net.cirellium.commons.core.service.ServiceHandler;

public abstract class CirelliumBukkitPlugin<P extends CirelliumBukkitPlugin<P>> extends JavaPlugin implements CirelliumPlugin {

    private ServiceHandler serviceHandler;
    
    @Override
    public void onLoad() {
        load();
        serviceHandler = new ServiceHandler();
    }

    @Override
    public void onEnable() {
        serviceHandler.loadServices();

        enable();
    }

    @Override
    public void onDisable() {
        disable();
    }

    public abstract P getInstance();

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