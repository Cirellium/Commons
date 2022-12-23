package net.cirellium.commons.manager;

import net.cirellium.commons.plugin.CirelliumPlugin;

public abstract class Manager {
    
    private CirelliumPlugin plugin;
    private ManagerType type;

    public Manager(CirelliumPlugin plugin, ManagerType type) {
        this.plugin = plugin;
        this.type = type;
    }

    protected CirelliumPlugin getPlugin() { return plugin; }

    public abstract void initialize();
    public abstract void shutdown();

}