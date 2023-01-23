/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Thu Jan 05 2023 10:20:02
*
* ArgumentTypeHandler.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation.argument;

import java.util.List;
import java.util.Set;

import org.bukkit.command.CommandSender;

public interface ArgumentTypeHandler<T> {
    
    T parse(CommandSender sender, String argument);

    List<String> tabComplete(CommandSender sender, Set<String> argumentSet, String argument);

    List<?> getPossibleResults();

    boolean supports(Class<?> clazz);
    
}