package net.cirellium.commons.bukkit.inv.provider;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.button.implementation.ButtonBuilder;
import net.cirellium.commons.bukkit.inv.button.implementation.ItemBuilder;
import net.cirellium.commons.bukkit.inv.click.ClickResponse;
import net.cirellium.commons.common.util.Provider;

public interface ButtonProvider extends Provider<Button, Integer> {
    
    @Override
    Button provide(Integer slot);

    ButtonProvider backgroundButtonProvider = (slot) -> new ButtonBuilder()
            .itemBuilder(new ItemBuilder(Material.STONE_PICKAXE)
                .displayName("§r ")
                // .flag(ItemFlag.HIDE_ATTRIBUTES)
                .flag(ItemFlag.HIDE_ENCHANTS)
                .flag(ItemFlag.HIDE_POTION_EFFECTS)
                .flag(ItemFlag.HIDE_DESTROYS)
                .flag(ItemFlag.HIDE_PLACED_ON)
                .damage((int)(Math.random()*80))
                .amount(1))
            .slot(slot)
            .clickHandler((clickInformation) -> ClickResponse.closeInventory())
            .build();

}