package net.cirellium.commons.bukkit.command.annotation.defaults;

import org.bukkit.command.CommandSender;

import net.cirellium.commons.common.command.annotations.Argument;
import net.cirellium.commons.common.command.annotations.Command;
import net.cirellium.commons.common.command.annotations.Command.SenderType;

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
        CommandSender sender,
        @Argument(name = "test") String test
    ) {
        sender.sendMessage("Test menu command executed: " + test);
    }

}