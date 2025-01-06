package net.cirellium.commons.bukkit.command.annotation.defaults;

import net.cirellium.commons.bukkit.command.annotation.invoker.BukkitCommandInvoker;
import net.cirellium.commons.common.command.annotation.annotations.Argument;
import net.cirellium.commons.common.command.annotation.annotations.Command;
import net.cirellium.commons.common.command.annotation.annotations.Command.SenderType;
import net.cirellium.commons.common.command.annotation.annotations.SubCommand;
import net.cirellium.commons.common.message.MessageKey.Default;

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
    public void cirellium(BukkitCommandInvoker sender) {
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
        BukkitCommandInvoker sender,
        @Argument(name = "test") String[] test
    ) {
        // sender.sendMessage("Test command executed: " + Arrays.stream(test).collect(Collectors.joining(" ")));

        sender.sendMessage(Default.TEST.placeholder("test", String.join(" ", test)));
    }

    @SubCommand(label = "help", mainCommand = "cirellium")  
    public void helpCommand(BukkitCommandInvoker sender) {
        sender.sendMessage("Help command executed");
    }
}