/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 17:00:20
*
* PlayerArgumentType.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation.argument.implementation;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.cirellium.commons.bukkit.command.annotation.argument.ArgumentTypeHandler;

public class PlayerArgumentType implements ArgumentTypeHandler<Player> {

    @Override
    public Player parse(CommandSender sender, String argument) {
        return Bukkit.getOnlinePlayers().stream().filter(player -> player.getName().equals(argument)).findFirst().orElse(null);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Set<String> argumentSet, String argument) {
        return Bukkit.getOnlinePlayers().stream().filter(player -> player.getName().startsWith(argument)).map(player -> player.getName()).toList();
    }

    @Override
    public List<?> getPossibleResults() {
        return Bukkit.getOnlinePlayers().stream().toList();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Player.class);
    }
    
}
