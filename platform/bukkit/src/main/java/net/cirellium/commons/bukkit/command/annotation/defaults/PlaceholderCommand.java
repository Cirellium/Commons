package net.cirellium.commons.bukkit.command.annotation.defaults;

import org.bukkit.command.CommandSender;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.common.command.annotation.annotations.Argument;
import net.cirellium.commons.common.command.annotation.annotations.Command;
import net.cirellium.commons.common.command.annotation.annotations.Command.SenderType;
import net.cirellium.commons.common.util.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class PlaceholderCommand {
    
    @Command(
        label = "placeholder", 
        permission = "",
        description = "Placeholder command",
        senderType = SenderType.CONSOLE,
        async = false,
        debug = true
    )
    public void placeholder(
        CommandSender sender,
        @Argument(name = "test") String test
    ) {
        CirelliumBukkitPlugin.getProvidingPlugin(getClass()).getLogger().info("Placeholder command: " + test);

        Component testMessage = Message.TEST.placeholder("test", test).getComponent();

        sender.sendMessage(LegacyComponentSerializer.legacy('§').serialize(testMessage));
    }
}