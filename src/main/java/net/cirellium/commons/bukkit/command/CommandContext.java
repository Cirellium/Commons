/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Wed Jan 04 2023 23:13:52
*
* CommandContext.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command;

import org.bukkit.command.CommandSender;

public class CommandContext {
    
    private CommandSender commandSender;

    private CommandArgument[] commandArguments;

    public CommandContext(CommandSender commandSender, CommandArgument[] commandArguments) {
        this.commandSender = commandSender;
        this.commandArguments = commandArguments;
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }

    public CommandArgument[] getCommandArguments() {
        return commandArguments;
    }

}