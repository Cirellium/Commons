/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 16:59:57
*
* BooleanArgumentType.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation.adapter.implementation;

import java.util.List;
import java.util.Set;

import org.bukkit.command.CommandSender;

import net.cirellium.commons.bukkit.command.annotation.adapter.ArgumentTypeAdapter;

public class BooleanTypeAdapter implements ArgumentTypeAdapter<Boolean> {

    List<String> validBooleans = List.of("true", "false", "yes", "no", "y", "n", "0", "1");

    @Override
    public Boolean parse(CommandSender sender, String argument) {
        if (!isValidBoolean(argument)) {
            sender.sendMessage("Invalid boolean: " + argument);
            return null;
        }

        return Boolean.parseBoolean(argument);
    }
    
    @Override
    public List<String> tabComplete(CommandSender sender, Set<String> argumentSet, String argument) {
        return List.of("true", "false");
    }

    private boolean isValidBoolean(String argument) {
        return validBooleans.contains(argument.toLowerCase());
    }

    @Override
    public List<?> getPossibleResults() {
        return validBooleans;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Boolean.class) || clazz.equals(boolean.class);
    }
}