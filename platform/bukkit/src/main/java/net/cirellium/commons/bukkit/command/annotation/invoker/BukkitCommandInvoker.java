package net.cirellium.commons.bukkit.command.annotation.invoker;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import net.cirellium.commons.bukkit.command.annotation.BukkitCommandHandler;
import net.cirellium.commons.common.command.sender.CommandInvoker;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;

public class BukkitCommandInvoker implements CommandInvoker {

    private final CommandSender sender;
    private final Audience audience;

    private @Nullable Player player;

    public BukkitCommandInvoker(CommandSender sender) {
        this.sender = sender;
        this.audience = BukkitCommandHandler.getInstance().getPlugin().adventure().sender(sender);
        this.player = (sender instanceof Player player) ? player : null;
    }

    @Override
    public void sendMessage(ComponentLike message) {
        audience.sendMessage(message);
    }

    public void sendMessage(String message) {
        audience.sendMessage(Component.text(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public Player getPlayer() {
        return player != null ? player : (player = (Player) sender);
    }

    public CommandSender getSender() {
        return sender;
    }
}