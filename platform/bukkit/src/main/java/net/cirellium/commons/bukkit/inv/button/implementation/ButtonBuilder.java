package net.cirellium.commons.bukkit.inv.button.implementation;

import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.click.ClickHandler;

public class ButtonBuilder implements Button.Builder {

    private Button.Builder.Item itemBuilder;
    private int slot;
    private ClickHandler clickHandler;

    @Override
    public Button.Builder itemBuilder(Button.Builder.Item itemBuilder) {
        this.itemBuilder = itemBuilder;
        return this;
    }

    @Override
    public Button.Builder slot(int slot) {
        this.slot = slot;
        return this;
    }

    @Override
    public Button.Builder clickHandler(ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    @Override
    public Button build() {
        return new Button(itemBuilder.build(), slot, clickHandler);
    }
}