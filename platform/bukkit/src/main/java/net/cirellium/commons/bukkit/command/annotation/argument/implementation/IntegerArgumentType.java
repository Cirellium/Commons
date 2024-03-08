/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 17:00:10
*
* IntegerArgumentType.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation.argument.implementation;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.command.CommandSender;

import net.cirellium.commons.bukkit.command.annotation.argument.ArgumentTypeHandler;

public class IntegerArgumentType implements ArgumentTypeHandler<Integer> {

    @Override
    public Integer parse(CommandSender sender, String argument) {
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cPlease enter a valid integer.");
        }
        return null;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Set<String> argumentSet, String argument) {
        // Return possible matching integers
        return Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    }

    @Override
    public List<?> getPossibleResults() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Integer.class) || clazz.equals(int.class);
    }
    
    
}