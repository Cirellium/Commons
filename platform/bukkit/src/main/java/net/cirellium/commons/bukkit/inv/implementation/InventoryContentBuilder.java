package net.cirellium.commons.bukkit.inv.implementation;

import net.cirellium.commons.bukkit.inv.button.Button;

public class InventoryContentBuilder implements InventoryContent.Builder {

    private final InventoryContent content;

    public InventoryContentBuilder() {
        this.content = new InventoryContent();
    }

    public InventoryContentBuilder empty() {
        content.buttons.clear();
        return this;
    }

    public InventoryContentBuilder addButton(Button button) {
        content.addButton(button);
        return this;
    }

    public InventoryContent build() {
        return content;
    }
}