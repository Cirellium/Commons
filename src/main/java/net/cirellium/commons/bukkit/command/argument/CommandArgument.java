/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Thu Jan 05 2023 10:20:02
*
* CommandArgument.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.argument;

import java.util.List;

import net.cirellium.commons.bukkit.command.CommandContext;

public interface CommandArgument<T> {
    
    T transform(CommandContext context, String argument);

    List<String> tabComplete(CommandContext context, String argument);
    
}