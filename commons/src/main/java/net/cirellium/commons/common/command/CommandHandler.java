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

public class CommandHandler<P extends CirelliumPlugin<P>> {
    
    private static CommandHandler<?> instance;

    protected final P plugin;

    protected CommandRegistry registry;

    protected Logger logger;

    public CommandHandler(P plugin) {
        instance = this;
        this.logger = new SimpleCirelliumLogger(plugin.getPlatform(), "CommandsAnnotated2");
        this.plugin = plugin;
    }

    public CommandRegistry getRegistry() { return registry; }

    public Logger getLogger() { return logger; }

    public static CommandHandler<?> getInstance() { return instance; }

}