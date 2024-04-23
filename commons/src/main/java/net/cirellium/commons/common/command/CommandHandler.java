/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 14:57:24
*
* CommandHandler.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.command;

import java.util.logging.Logger;

import net.cirellium.commons.common.logger.SimpleCirelliumLogger;
import net.cirellium.commons.common.plugin.CirelliumPlugin;

public class CommandHandler {
    
    protected static CommandHandler INSTANCE;

    protected final CirelliumPlugin<?> plugin;

    protected CommandRegistry registry;

    protected Logger logger;

    public CommandHandler(CirelliumPlugin<?> plugin) {
        INSTANCE = this;
        this.logger = new SimpleCirelliumLogger(plugin.getPlatform(), "CommandsAnnotated2");
        this.plugin = plugin;
    }

    public CirelliumPlugin<?> getPlugin() { return plugin; }

    public CommandRegistry getRegistry() { return registry; }

    public Logger getLogger() { return logger; }

    public static CommandHandler getInstance() { return INSTANCE; }

}