package net.cirellium.commons.bukkit.command.annotation.defaults;

import org.bukkit.command.CommandSender;

import net.cirellium.commons.bukkit.command.abstraction.AbstractCommand.SenderType;
import net.cirellium.commons.bukkit.command.annotation.annotations.Argument;
import net.cirellium.commons.bukkit.command.annotation.annotations.Command;

public class TestMenuCommand {
    
    @Command(
        names = {"test", "testmenu"}, 
        permission = "",
        description = "Just a simple test menu command",
        senderType = SenderType.PLAYER,
        async = false,
        debug = true
    )
    public void menu(
        CommandSender sender,
        @Argument(name = "test", wildcard = true) String test
    ) {
        sender.sendMessage("Test menu command executed: " + test);
    }

}