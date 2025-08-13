/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Wed Jan 04 2023 23:13:52
*
* CommandContext.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.abstraction;

import org.bukkit.command.CommandSender;

public class CommandContext {
    
    private CommandSender commandSender;

    public CommandContext(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }
}