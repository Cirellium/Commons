/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 17:00:24
*
* StringArgumentType.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation.adapter.implementation;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.command.CommandSender;

import net.cirellium.commons.bukkit.command.annotation.adapter.ArgumentTypeAdapter;

public class StringTypeAdapter implements ArgumentTypeAdapter<String> {

    @Override
    public String parse(CommandSender sender, String argument) {
        return argument;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Set<String> argumentSet, String argument) {
        return Collections.emptyList();
    }

    @Override
    public List<?> getPossibleResults() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(String.class);
    }
    
}