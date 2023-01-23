package net.cirellium.commons.bukkit.command.annotation.defaults;

import org.bukkit.entity.Player;

import net.cirellium.commons.bukkit.command.abstraction.AbstractCommand.SenderType;
import net.cirellium.commons.bukkit.command.annotation.annotations.Argument;
import net.cirellium.commons.bukkit.command.annotation.annotations.Command;
import net.cirellium.commons.common.util.Message;

public class PlaceholderCommand {
    
    @Command(names = {"placeholder"}, 
        permission = "",
        description = "Placeholder command",
        senderType = SenderType.PLAYER,
        async = false,
        debug = true)
    public void placeholder(Player player, @Argument(name = "test", wildcard = true) String test) {
        player.sendMessage(Message.TEST.placeholder("test", test).getComponent());
    }

}