package net.cirellium.commons.bukkit.command.annotation.defaults;

import org.bukkit.command.CommandSender;

import net.cirellium.commons.bukkit.command.abstraction.AbstractCommand.SenderType;
import net.cirellium.commons.bukkit.command.annotation.annotations.Argument;
import net.cirellium.commons.bukkit.command.annotation.annotations.Command;

public class TestCommand {
    
    @Command(
        names = {"test", "testcommand"}, 
        permission = "",
        description = "Just a simple test command",
        senderType = SenderType.CONSOLE,
        async = false,
        debug = true
    )
    public void testCommand(
        CommandSender sender,
        @Argument(name = "test", wildcard = true) String test
    ) {
        sender.sendMessage("Test command executed: " + test);
    }

}