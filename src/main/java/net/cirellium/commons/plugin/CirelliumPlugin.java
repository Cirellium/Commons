package net.cirellium.commons.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class CirelliumPlugin extends JavaPlugin {

    private static CirelliumPlugin instance;

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static CirelliumPlugin getInstance() {
        return instance;
    }

    public abstract void load();

    public abstract void enable();

    public abstract void disable();

    
}