package net.cirellium.commons.bukkit.command.annotation.defaults;

import net.cirellium.commons.bukkit.command.annotation.invoker.BukkitCommandInvoker;
import net.cirellium.commons.common.command.annotation.annotations.Argument;
import net.cirellium.commons.common.command.annotation.annotations.Command;
import net.cirellium.commons.common.command.annotation.annotations.Command.SenderType;

public class TestMenuCommand {
    
    @Command(
        label = "testmenu",
        aliases = { "testmenucommand", "tm" },
        permission = "",
        description = "Just a simple test menu command",
        senderType = SenderType.PLAYER,
        async = false,
        debug = true
    )
    public void menu(
        BukkitCommandInvoker sender,
        @Argument(name = "test") String test
    ) {
        sender.sendMessage("Test menu command executed: " + test);
    }
}