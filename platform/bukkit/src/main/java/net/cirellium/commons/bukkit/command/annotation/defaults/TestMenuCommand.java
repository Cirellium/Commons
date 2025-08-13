package net.cirellium.commons.bukkit.command.annotation.defaults;

import net.cirellium.commons.bukkit.command.annotation.invoker.BukkitCommandInvoker;
import net.cirellium.commons.bukkit.inv.content.InventoryDesign;
import net.cirellium.commons.bukkit.inv.content.InventoryDesign.InventorySize;
import net.cirellium.commons.bukkit.inv.content.implementation.InventoryContentBuilder;
import net.cirellium.commons.bukkit.inv.content.implementation.InventoryDesignBuilder;
import net.cirellium.commons.bukkit.inv.implementation.NormalInventory;
import net.cirellium.commons.bukkit.inv.provider.ButtonProvider;
import net.cirellium.commons.bukkit.inv.provider.InventoryProvider;
import net.cirellium.commons.common.command.annotation.annotations.Argument;
import net.cirellium.commons.common.command.annotation.annotations.Command;
import net.cirellium.commons.common.command.annotation.annotations.Command.SenderType;

public class TestMenuCommand {

    @Command(label = "testmenu", aliases = { "testmenucommand",
            "tm" }, permission = "", description = "Just a simple test menu command", senderType = SenderType.PLAYER, async = false, debug = true)
    public void menu(
            BukkitCommandInvoker sender,
            @Argument(name = "test") String test) { 

        sender.sendMessage("Test menu command executed: " + test);

        InventoryDesign invDesign = new InventoryDesignBuilder()
                .size(InventorySize.FOUR_ROWS)
                .title("§aTest Menu")
                .content(new InventoryContentBuilder().fillBackground(ButtonProvider.backgroundButtonProvider))
                .build();

        NormalInventory inv = (NormalInventory) InventoryProvider.defaultInventoryProvider.provide(invDesign);

        invDesign.content().addButton(ButtonProvider.backgroundButtonProvider.provide(4));

        inv.openInventory(sender.getPlayer());
    }
}