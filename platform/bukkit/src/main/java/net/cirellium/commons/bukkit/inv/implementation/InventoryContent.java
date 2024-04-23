package net.cirellium.commons.bukkit.inv.implementation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import net.cirellium.commons.bukkit.inv.button.Button;

/**
 * This class represents the contents of an inventory.
 * 
 * @author Fear
 */
public class InventoryContent implements Iterable<ItemStack> {
    
    public final Map<Integer, Button> buttons;

    public InventoryContent() {
        this.buttons = new HashMap<>();
    }

    public Button get(int index) {
        return buttons.get(index);
    }

    public void addButton(Button button) {
        buttons.put(button.slot(), button);
    }

    public void removeButton(Button button) {
        buttons.remove(button.slot());
    }

    public HashMap<Integer, Button> getButtons() {
        return new HashMap<>(buttons);
    }

    @Override
    public Iterator<ItemStack> iterator() {
        return buttons.values().stream().map(Button::itemStack).iterator();
    }

    /**
     * This interface provides a builder for the {@link InventoryContent} class.
     * 
     * @author Fear
     */
    public interface Builder {

        /**
         * Creates a new empty {@link InventoryContent.Builder}.
         * 
         * @return An empty {@link InventoryContent.Builder}.
         */
        Builder empty();

        /**
         * Adds a {@link Button} to the {@link InventoryContent.Builder} object.
         * 
         * @param button The button to add
         * @return This builder object
         */
        Builder addButton(Button button);

        /**
         * Builds the {@link InventoryContent} object from this builder.
         * 
         * @return The {@link InventoryContent} object
         */
        InventoryContent build();
    }
}