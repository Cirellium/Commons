package net.cirellium.commons.bukkit.command.argument.implementation;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.cirellium.commons.bukkit.command.CommandContext;
import net.cirellium.commons.bukkit.command.argument.CommandArgument;

public class PlayerArgument implements CommandArgument<Player> {

    @Override
    public Player transform(CommandContext context, String argument) {
        return Bukkit.getOnlinePlayers().stream().filter(player -> player.getName().equals(argument)).findFirst().orElse(null);
    }

    @Override
    public List<String> tabComplete(CommandContext context, String argument) {
        return Bukkit.getOnlinePlayers().stream().filter(player -> player.getName().startsWith(argument)).map(player -> player.getName()).toList();
    }
    
}
