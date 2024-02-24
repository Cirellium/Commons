package net.cirellium.commons.bukkit.inv.provider;

import net.cirellium.commons.bukkit.inv.InventoryBase;
import net.cirellium.commons.bukkit.inv.content.InventoryDesign;
import net.cirellium.commons.bukkit.inv.implementation.NormalInventory;
import net.cirellium.commons.common.util.Provider;

public interface InventoryProvider extends Provider<InventoryBase, InventoryDesign> {
    
    @Override
    InventoryBase provide(InventoryDesign design);

    InventoryProvider defaultInventoryProvider = (design) -> new NormalInventory(design.size(), design.title(), design.content()) {};

}