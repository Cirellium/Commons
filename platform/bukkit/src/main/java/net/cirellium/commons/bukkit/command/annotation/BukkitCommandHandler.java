package net.cirellium.commons.bukkit.command.annotation;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.common.command.CommandHandler;

public class BukkitCommandHandler<P extends CirelliumBukkitPlugin<P>> extends CommandHandler<P> {

    public BukkitCommandHandler(P plugin) {
        super(plugin);
        
        super.registry = new BukkitCommandRegistry(plugin.getPluginName().toLowerCase());
    }

    public static BukkitCommandHandler<?> getInstance() {
        return (BukkitCommandHandler<?>) instance;
    }
}