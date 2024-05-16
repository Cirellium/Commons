package net.cirellium.commons.bukkit.inv.implementation.special;

import org.bukkit.scheduler.BukkitRunnable;

import net.cirellium.commons.bukkit.inv.InventoryBase;
import net.cirellium.commons.bukkit.inv.content.InventoryContent;
import net.cirellium.commons.bukkit.inv.content.InventoryDesign.InventorySize;

/**
 * TODO implement different animations using {@link BukkitRunnable}s
*/
public abstract class AnimatedInventory extends InventoryBase {


    public AnimatedInventory(InventorySize size, String title, InventoryContent content) {
        super(size, title, content);
    }

    public AnimatedInventory(int size, String title, InventoryContent content) {
        super(size, title, content);
    }   

    
}