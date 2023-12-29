/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 14:57:24
*
* CommandHandler.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation;

import java.util.logging.Logger;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.common.logger.CirelliumLogger;
import net.cirellium.commons.common.version.Platform;

public class CommandHandler<P extends CirelliumBukkitPlugin> {
    
    private static CommandHandler<?> instance;

    protected final CirelliumBukkitPlugin plugin;

    private CommandRegistry registry;

    private Logger logger;

    public CommandHandler(P plugin) {
        instance = this;
        this.logger = new CirelliumLogger(Platform.BUKKIT, "CommandsAnnotated2");
        this.plugin = plugin;
        this.registry = new CommandRegistry(plugin.getPluginName().toLowerCase());
    }

    public CommandRegistry getRegistry() { return registry; }

    public Logger getLogger() { return logger; }

    public static CommandHandler<?> getInstance() { return instance; }

}