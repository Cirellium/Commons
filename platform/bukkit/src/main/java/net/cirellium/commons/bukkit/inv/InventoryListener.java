package net.cirellium.commons.bukkit.inv;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;

import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.click.ClickHandler.ClickInformation;
import net.cirellium.commons.bukkit.inv.click.ClickResponse;

public class InventoryListener implements Listener {

    @EventHandler
    public void invClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player))
            return;
        if (e.getClickedInventory() == null)
            return;
        if (e.getCurrentItem() == null)
            return;
        if (e.getView().getTopInventory() == null)
            return;
        if (e instanceof InventoryCreativeEvent)
            return;
        if (e.getClickedInventory().getType() == InventoryType.PLAYER)
            return;
        if (e.getClickedInventory().getHolder() == null)
            return;
        if (!(e.getClickedInventory().getHolder() instanceof InventoryBase))
            return;

        InventoryBase inv = (InventoryBase) e.getClickedInventory().getHolder();

        if (inv == null)
            return;

        Button clicked = inv.getContent().get(e.getSlot());

        if (clicked == null) {
            e.setCancelled(true);
            return;
        }

        ClickInformation clickInfo = new ClickInformation(e);

        ClickResponse response = inv.onClick(clickInfo, clicked);

        e.setCancelled(response.process(clickInfo));
    }

    @EventHandler
    public void invDrag(InventoryDragEvent e) {
        if (!(e.getWhoClicked() instanceof Player))
            return;
        if (e.getInventory() == null)
            return;
        if (e.getInventory().getHolder() == null)
            return;
        if (!(e.getInventory().getHolder() instanceof InventoryBase))
            return;

        InventoryBase inv = (InventoryBase) e.getInventory().getHolder();

        if (inv == null)
            return;

        for (int slot : e.getRawSlots()) {
            if (inv.getContent().getButtons().containsKey(slot)) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void invClose(InventoryCloseEvent e) {
        if (e.getInventory() == null)
            return;
        if (e.getView().getTopInventory() == null)
            return;
        if (e.getInventory().getHolder() == null)
            return;
        if (!(e.getView().getTopInventory().getHolder() instanceof InventoryBase))
            return;

        InventoryBase inv = (InventoryBase) e.getView().getTopInventory().getHolder();

        if (inv == null)
            return;

        inv.closeInventory((Player) e.getPlayer());
    }
}