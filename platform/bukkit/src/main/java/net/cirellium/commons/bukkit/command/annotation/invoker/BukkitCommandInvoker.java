package net.cirellium.commons.bukkit.command.annotation.invoker;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.cirellium.commons.common.command.sender.CommandInvoker;

public class BukkitCommandInvoker implements CommandInvoker {
    
    private final CommandSender sender;

    public BukkitCommandInvoker(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public boolean isPlayer() {
        return sender instanceof Player;
    }
}