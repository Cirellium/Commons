/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 17:00:20
*
* PlayerArgumentType.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation.adapter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.cirellium.commons.common.command.annotation.adapter.implementation.CommandArgumentTypeAdapter;
import net.cirellium.commons.common.command.sender.CommandInvoker;

public class PlayerTypeAdapter implements CommandArgumentTypeAdapter<Player> {

    @Override
    public Player parse(CommandInvoker sender, String argument) {
        return Bukkit.getOnlinePlayers().stream().filter(player -> player.getName().equals(argument)).findFirst().orElse(null);
    }

    @Override
    public List<String> tabComplete(CommandInvoker sender, Set<String> argumentSet, String argument) {
        return Bukkit.getOnlinePlayers().stream().filter(player -> player.getName().startsWith(argument)).map(player -> player.getName()).collect(Collectors.toList());
    }

    @Override
    public List<Player> getPossibleResults() {
        return Bukkit.getOnlinePlayers().stream().collect(Collectors.toList());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Player.class);
    }
    
}
