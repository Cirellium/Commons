package net.cirellium.commons.bukkit.inv;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

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
    public void invClose(InventoryCloseEvent e) {
        InventoryBase inv = (InventoryBase) e.getInventory().getHolder();

        if (inv == null)
            return;

        inv.closeInventory((Player) e.getPlayer());
    }
}