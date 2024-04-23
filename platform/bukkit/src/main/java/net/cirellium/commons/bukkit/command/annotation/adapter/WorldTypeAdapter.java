/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 17:00:29
*
* WorldArgumentType.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation.adapter;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;

import net.cirellium.commons.common.command.annotation.adapter.ArgumentTypeAdapter;
import net.cirellium.commons.common.command.sender.CommandInvoker;

public class WorldTypeAdapter implements ArgumentTypeAdapter<World> {

    @Override
    public World parse(CommandInvoker sender, String argument) {
        return getPossibleResults().stream().filter(world -> world.getName().equalsIgnoreCase(argument)).findFirst().orElse(null);
    }

    @Override
    public List<String> tabComplete(CommandInvoker sender, Set<String> argumentSet, String argument) {
        return getPossibleResults().stream().filter(w -> w.getName().startsWith(argument)).map(World::getName).toList();
    }

    @Override
    public List<World> getPossibleResults() {
        return Bukkit.getServer().getWorlds();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(World.class);
    }
}