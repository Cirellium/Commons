package net.cirellium.commons.bukkit.inv.button;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.cirellium.commons.bukkit.inv.click.ClickHandler;

/**
 * This record represents a clickable button in a custom inventory.
 * 
 * It contains the item stack to be displayed, the slot in the inventory and the click action to be executed when the button is clicked.
 * 
 * @author Fear
 */
public record Button(ItemStack itemStack, int slot, ClickHandler clickHandler) {

    /**
     * This class provides a builder for the {@link Button} record.
     * 
     * It allows to create a button with a fluent API.
     * 
     * @author Fear
     */
    public interface Builder {
        /**
         * Sets the slot of the button.
         * 
         * @param slot The slot of the button
         * @return The builder
         */
        Builder slot(int slot);

        /**
         * Sets the click handler of the button.
         * 
         * @param clickHandler The click handler of the button
         * @return The builder
         */
        Builder clickHandler(ClickHandler clickHandler);

        /**
         * Sets the item builder of the button.
         * 
         * @param itemStack The item builder of the button
         * @return The builder
         */
        Builder itemBuilder(Item itemBuilder);

        /**
         * Builds the button.
         * 
         * @return The button
         */
        Button build();

        /**
         * This class provides a builder for the {@link ItemStack} class.
         * 
         * It allows to create an item stack with a fluent API.
         * 
         * @author Fear
         * @apiNote This builder is preferably used with the {@link Button.Builder} class.
         */
        public interface Item {
            /**
             * Sets the material of the item stack.
             * 
             * @param material The material of the item stack
             * @return The builder
             */
            Item setMaterial(Material material);

            /**
             * Sets the display name of the item stack.
             * 
             * @param displayName The display name of the item stack
             * @return The builder
             */
            Item setDisplayName(String displayName);

            /**
             * Sets the amount of the item stack.
             * 
             * @param amount The amount of the item stack
             * @return The builder
             */
            Item setAmount(int amount);

            /**
             * Adds a lore line to the item stack.
             * 
             * @param lore The lore line to add
             * @return The builder
             */
            Item addLore(String lore);

            /**
             * Sets the lore of the item stack.
             * 
             * @param lore The lore of the item stack
             * @return The builder
             */
            Item setLore(String... lore);

            /**
             * Sets the lore of the item stack.
             * 
             * @param lore The lore of the item stack
             * @return The builder
             */
            Item setLore(List<String> lore);

            /**
             * Makes the item stack glow optionally.
             * 
             * @param glowing Whether the item stack should glow
             * @return The builder
             */
            Item setGlowing(boolean glowing);

            /**
             * Builds the item stack.
             * 
             * @return The item stack
             */
            ItemStack build();
        }
    }
}