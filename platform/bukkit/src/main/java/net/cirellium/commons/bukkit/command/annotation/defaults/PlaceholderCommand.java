package net.cirellium.commons.bukkit.command.annotation.defaults;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.bukkit.command.annotation.invoker.BukkitCommandInvoker;
import net.cirellium.commons.common.command.annotation.annotations.Argument;
import net.cirellium.commons.common.command.annotation.annotations.Command;
import net.cirellium.commons.common.command.annotation.annotations.Command.SenderType;
import net.cirellium.commons.common.message.MessageKey.Default;

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
        BukkitCommandInvoker sender,
        @Argument(name = "test") String test
    ) {
        CirelliumBukkitPlugin.getProvidingPlugin(getClass()).getLogger().info("Placeholder command: " + test);

        sender.sendMessage(Default.TEST.placeholder("test", test).getComponent());
    }
}