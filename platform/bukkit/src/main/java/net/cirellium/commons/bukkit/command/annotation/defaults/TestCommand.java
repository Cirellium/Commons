package net.cirellium.commons.bukkit.command.annotation.defaults;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;

import net.cirellium.commons.common.command.annotation.annotations.Argument;
import net.cirellium.commons.common.command.annotation.annotations.Command;
import net.cirellium.commons.common.command.annotation.annotations.SubCommand;
import net.cirellium.commons.common.command.annotation.annotations.Command.SenderType;

public class TestCommand {
    
    @Command(
        label = "cirellium",
        aliases = { "cr" },
        permission = "",
        description = "Cirellium main command",
        senderType = SenderType.CONSOLE,
        async = false,
        debug = true
    )
    public void cirellium(CommandSender sender) {
        sender.sendMessage("Cirellium command executed");
    }

    @SubCommand(
        label = "test",
        aliases = { "testcommand" },
        mainCommand = "cirellium",
        permission = "",
        description = "Just a simple test command"
    )
    public void testCommand(
        CommandSender sender,
        @Argument(name = "test") String[] test
    ) {
        sender.sendMessage("Test command executed: " + Arrays.stream(test).collect(Collectors.joining(" ")));
    }

    @SubCommand(label = "help", mainCommand = "cirellium")  
    public void helpCommand(CommandSender sender) {
        sender.sendMessage("Help command executed");
    }



}