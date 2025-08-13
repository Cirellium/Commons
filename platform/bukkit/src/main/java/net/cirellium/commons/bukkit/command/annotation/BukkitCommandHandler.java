package net.cirellium.commons.bukkit.command.annotation;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.common.command.CommandHandler;

public class BukkitCommandHandler extends CommandHandler {

    public BukkitCommandHandler(CirelliumBukkitPlugin plugin) {
        super(plugin);
        
        super.registry = new BukkitCommandRegistry(plugin.getPluginName().toLowerCase());
    }

    public CirelliumBukkitPlugin getPlugin() {
        return (CirelliumBukkitPlugin) super.plugin;
    }

    public static BukkitCommandHandler getInstance() {
        return (BukkitCommandHandler) INSTANCE;
    }
}