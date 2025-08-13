package net.cirellium.commons.bukkit.inv.implementation;

import net.cirellium.commons.bukkit.inv.InventoryBase;
import net.cirellium.commons.bukkit.inv.content.InventoryContent;
import net.cirellium.commons.bukkit.inv.content.InventoryDesign.InventorySize;

public abstract class NormalInventory extends InventoryBase {

    public NormalInventory(InventorySize size, String title, InventoryContent content) {
        super(size, title, content);
    }

    public NormalInventory(int size, String title, InventoryContent content) {
        super(size, title, content);
    }    
}