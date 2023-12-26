package net.cirellium.commons.bukkit.command.annotation.defaults;

import org.bukkit.command.CommandSender;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.bukkit.command.abstraction.AbstractCommand.SenderType;
import net.cirellium.commons.bukkit.command.annotation.annotations.Argument;
import net.cirellium.commons.bukkit.command.annotation.annotations.Command;
import net.cirellium.commons.common.util.Message;

public class PlaceholderCommand {
    
    @Command(
        names = {"placeholder"}, 
        permission = "",
        description = "Placeholder command",
        senderType = SenderType.CONSOLE,
        async = false,
        debug = true
    )
    public void placeholder(
        CommandSender sender,
        @Argument(name = "test", wildcard = true) String test
    ) {
        CirelliumBukkitPlugin.getProvidingPlugin(getClass()).getLogger().info("Placeholder command: " + test);

        sender.sendMessage(Message.TEST.placeholder("test", test).getComponent().toString());
    }
}