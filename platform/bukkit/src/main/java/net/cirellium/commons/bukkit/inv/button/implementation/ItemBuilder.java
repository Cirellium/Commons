package net.cirellium.commons.bukkit.inv.button.implementation;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.button.Button.Builder.Item;

public class ItemBuilder implements Button.Builder.Item {

    private Material material;
    private String displayName;
    private int amount;
    private List<String> lore;
    private boolean glowing;

    @Override
    public Item setMaterial(Material material) {
        this.material = material;
        return this;
    }

    @Override
    public Item setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    @Override
    public Item setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public Item addLore(String lore) {
        this.lore.add(lore);
        return this;
    }

    @Override
    public Item setLore(String... lore) {
        this.lore.clear();

        for (String line : lore) {
            this.lore.add(line);
        }
        return this;
    }

    @Override
    public Item setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    @Override
    public Item setGlowing(boolean glowing) {
        this.glowing = glowing;
        return this;
    }

    @Override
    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLore(lore);

        if (glowing) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
        }

        itemStack.setItemMeta(meta);

        return itemStack;
    }    
}