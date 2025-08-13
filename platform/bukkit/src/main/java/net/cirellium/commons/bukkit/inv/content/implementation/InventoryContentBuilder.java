package net.cirellium.commons.bukkit.inv.content.implementation;

import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.content.InventoryContent;
import net.cirellium.commons.bukkit.inv.content.InventoryDesign.InventorySize;
import net.cirellium.commons.bukkit.inv.provider.ButtonProvider;

public class InventoryContentBuilder implements InventoryContent.Builder {

    private final InventoryContent content;

    public InventoryContentBuilder() {
        this.content = new InventoryContent(InventorySize.SIX_ROWS.getSize());
    }

    public InventoryContentBuilder empty() {
        content.buttons.clear();
        return this;
    }

    public InventoryContentBuilder fillBackground(ButtonProvider provider) {
        for (int i = 0; i < content.getSize() - 1; i++) {
            content.addButton(provider.provide(i));
        }
        return this;
    }

    public InventoryContentBuilder fillBackground(Button button) {
        for (int i = 0; i < content.getButtons().size(); i++) {
            content.addButton(button);
        }
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