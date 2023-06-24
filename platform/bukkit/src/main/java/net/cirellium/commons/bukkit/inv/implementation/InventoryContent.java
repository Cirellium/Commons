package net.cirellium.commons.bukkit.inv.implementation;

import java.util.HashMap;
import java.util.Map;

import net.cirellium.commons.bukkit.inv.button.Button;

public class InventoryContent {
    
    private final Map<Integer, Button> buttons;

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

    public static class Builder {
        private final InventoryContent content;

        public Builder() {
            this.content = new InventoryContent();
        }

        public Builder empty() {
            content.buttons.clear();
            return this;
        }

        public Builder addButton(Button button) {
            content.addButton(button);
            return this;
        }

        public InventoryContent build() {
            return content;
        }
    }
}