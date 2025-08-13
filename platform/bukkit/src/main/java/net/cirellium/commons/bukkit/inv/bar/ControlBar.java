package net.cirellium.commons.bukkit.inv.bar;

import java.util.ArrayList;
import java.util.List;

import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.content.InventoryPosition;

public abstract class ControlBar {

    protected final List<Button> buttons;

    protected ControlBar(List<Button> buttons) {
        this.buttons = buttons;
    }

    public abstract void update();

    public List<Button> getButtons() {
        return new ArrayList<>(buttons);
    }

    public Button getButton(InventoryPosition position) {
        return buttons.get(position.getIndex());
    }

    public void setButton(InventoryPosition position, Button button) {
        buttons.set(position.getIndex(), button);
    }

    public void addButton(Button button) {
        buttons.add(button);
    }

    public void removeButton(Button button) {
        buttons.remove(button);
    }

    public void clear() {
        buttons.clear();
    }

    /**
     * A builder for the ControlBar class.
     * 
     * This builder allows the creation of a ControlBar with a fluent API.
     * 
     * @author Fear
     */
    public interface Builder {
        /**
         * Adds a button to a specific index.
         * 
         * @param index The index where the button should be added.
         * @param button The button to be added.
         * @return The builder.
         */
        Builder addButton(int index, Button button);

        /**
         * Adds a button to a specific position.
         * 
         * @param position The position where the button should be added.
         * @param button The button to be added.
         * @return The builder.
         */
        Builder addButton(InventoryPosition position, Button button);

        /**
         * Adds all specified buttons.
         * 
         * @param buttons The buttons to be added.
         * @return The builder.
         */
        Builder addButtons(List<Button> buttons);

        /**
         * Creates the ControlBar.
         * 
         * @return The created ControlBar.
         */
        ControlBar build();
    }
}
