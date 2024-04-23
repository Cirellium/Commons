package net.cirellium.commons.bukkit.inv.button;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.cirellium.commons.bukkit.inv.click.ClickHandler;

/**
 * This record represents a clickable button in a custom inventory.
 * 
 * It contains the item stack to be displayed, the slot in the inventory and the
 * click action to be executed when the button is clicked.
 * 
 * @author Fear
 */
public record Button(ItemStack itemStack, int slot, ClickHandler clickHandler) {

    /**
     * This interface provides a builder for the {@link Button} record.
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
         * @param itemBuilder The item builder of the button
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
         * @apiNote This builder is preferably used with the {@link Button.Builder}
         *          class.
         */
        public interface Item {

            /**
             * Edits the item using the provided consumer.
             *
             * @param consumer The consumer to apply changes to the item.
             * @return The current builder object.
             */
            Item edit(Consumer<ItemStack> consumer);

            /**
             * Sets the metadata of the item using the provided consumer.
             *
             * @param consumer The consumer to apply changes to the item metadata.
             * @return The current builder object.
             */
            Item meta(Consumer<ItemMeta> consumer);

            /**
             * Sets the metadata of the item using the provided consumer and metadata class.
             *
             * @param clazz The class of the metadata to apply changes to.
             * @param consumer The consumer to apply changes to the item metadata.
             * @return The current builder object.
             */
            <T extends ItemMeta> Item meta(Class<T> clazz, Consumer<T> consumer);

            /**
             * Sets the material of the item stack.
             * 
             * @param material The material of the item stack
             * @return The builder
             */
            Item material(Material material);

            /**
             * Sets the display name of the item stack.
             * 
             * @param displayName The display name of the item stack
             * @return The builder
             */
            Item displayName(String displayName);

            /**
             * Sets the amount of the item stack.
             * 
             * @param amount The amount of the item stack
             * @return The builder
             */
            Item amount(int amount);

            /**
             * Adds a lore line to the item stack.
             * 
             * @param lore The lore line to add
             * @return The builder
             */
            Item lore(String lore);

            /**
             * Sets the lore of the item stack.
             * 
             * @param lore The lore of the item stack
             * @return The builder
             */
            Item lore(String... lore);

            /**
             * Sets the lore of the item stack.
             * 
             * @param lore The lore of the item stack
             * @return The builder
             */
            Item lore(List<String> lore);

            /**
             * Makes the item stack glow optionally.
             * 
             * @param glowing Whether the item stack should glow
             * @return The builder
             */
            Item glowing(boolean glowing);

            Item enchantment(Enchantment enchantment);

            Item enchantment(Enchantment enchantment, int level);

            Item flag(ItemFlag flag);

            Item damage(int damage);

            Item unbreakable();

            /**
             * Builds the item stack.
             * 
             * @return The item stack
             */
            ItemStack build();
        }
    }
}