package net.cirellium.commons.bukkit.inv.content.implementation;

import net.cirellium.commons.bukkit.inv.content.InventoryContent;
import net.cirellium.commons.bukkit.inv.content.InventoryDesign;
import net.cirellium.commons.bukkit.inv.content.InventoryDesign.Builder;
import net.cirellium.commons.bukkit.inv.content.InventoryDesign.InventorySize;

public class InventoryDesignBuilder implements InventoryDesign.Builder {

    private InventorySize size;
    private String title;
    private InventoryContent.Builder contentBuilder;

    public InventoryDesignBuilder() {
        this.size = InventorySize.SIX_ROWS;
        this.title = "";
        this.contentBuilder = new InventoryContentBuilder();
    }

    @Override
    public Builder size(InventorySize size) {
        this.size = size;
        return this;
    }

    @Override
    public Builder title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public Builder content(InventoryContent.Builder builder) {
        this.contentBuilder = builder;
        return this;
    }

    @Override
    public InventoryDesign build() {
        return new InventoryDesign(size, title, contentBuilder.build());
    }
}