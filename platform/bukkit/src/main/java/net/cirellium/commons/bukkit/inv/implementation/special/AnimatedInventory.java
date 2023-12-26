package net.cirellium.commons.bukkit.inv.implementation.special;

import org.bukkit.scheduler.BukkitRunnable;

import net.cirellium.commons.bukkit.inv.implementation.InventoryBase;

/**
 * TODO implement different animations using {@link BukkitRunnable}s
*/
public abstract class AnimatedInventory extends InventoryBase {


    public AnimatedInventory(String title) {
        super(0, title);
    }

    
}