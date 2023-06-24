package net.cirellium.commons.bukkit.inv;

import org.bukkit.entity.Player;

import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.click.ClickHandler.ClickInformation;
import net.cirellium.commons.bukkit.inv.click.ClickResponse;
import net.cirellium.commons.bukkit.inv.implementation.InventoryBase;
import net.cirellium.commons.bukkit.inv.implementation.InventoryContent;

/**
 * This interface represents a custom inventory and is implemented by all types of inventoris.
 * 
 * @see InventoryBase
 * @author Fear
 */
public interface CustomInventory {

    void updateInventory();

    void openInventory(Player player);

    void closeInventory(Player player);

    ClickResponse onClick(ClickInformation clickInformation, Button clickedButton);

    InventoryContent getContent();
}