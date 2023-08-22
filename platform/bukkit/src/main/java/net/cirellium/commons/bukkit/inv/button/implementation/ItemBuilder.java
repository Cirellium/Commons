package net.cirellium.commons.bukkit.inv.button.implementation;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.button.Button.Builder.Item;

public class ItemBuilder implements Button.Builder.Item {

    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    @Override
    public Item edit(Consumer<ItemStack> consumer) {
        consumer.accept(itemStack);
        return this;
    }

    @Override
    public Item meta(Consumer<ItemMeta> consumer) {
        return edit(itemStack -> {
            ItemMeta itemMeta = itemStack.getItemMeta();

            consumer.accept(itemMeta);

            itemStack.setItemMeta(itemMeta);
        });
    }

    @Override
    public <T extends ItemMeta> Item meta(Class<T> clazz, Consumer<T> consumer) {
        return meta(itemMeta -> {
            if (clazz.isInstance(itemMeta)) consumer.accept(clazz.cast(itemMeta));
        });
    }

    @Override
    public Item material(Material material) {
        return edit(itemStack -> itemStack.setType(material));
    }

    @Override
    public Item displayName(String displayName) {
        return meta(itemMeta -> itemMeta.setDisplayName(displayName));
    }

    @Override
    public Item amount(int amount) {
        return edit(itemStack -> itemStack.setAmount(amount));
    }

    @Override
    public Item lore(String lore) {
        return meta(itemMeta -> {
            List<String> loreList = itemMeta.getLore();
            loreList.add(lore);
            itemMeta.setLore(loreList);
        });
    }

    @Override
    public Item lore(String... lore) {
        Item addedLore = null;

        for(String lorePart : lore) {
            addedLore = lore(lorePart);
        }
        return addedLore;
    }

    @Override
    public Item lore(List<String> lore) {
        
        return this;
    }

    @Override
    public Item glowing(boolean glowing) {
        
        return this;
    }

    @Override
    public Item enchantment(Enchantment enchantment) {
        
        return this;
    }

    @Override
    public Item enchantment(Enchantment enchantment, int level) {
        
        return this;
    }

    @Override
    public Item flag(ItemFlag flag) {
        
        return this;
    }

    @Override
    public Item damage(int damage) {
        
        return this;
    }

    @Override
    public Item unbreakable() {
        
        return this;
    }

    @Override
    public ItemStack build() {
        return itemStack;
    }

    
}