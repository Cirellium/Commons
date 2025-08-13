package net.cirellium.commons.bukkit.inv.bar.implementation;

import java.util.ArrayList;
import java.util.List;

import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.bar.ControlBar;
import net.cirellium.commons.bukkit.inv.content.InventoryPosition;

public class ControlBarBuilder implements ControlBar.Builder {

    private final List<Button> buttons = new ArrayList<>();

    @Override
    public ControlBar.Builder addButton(int index, Button button) {
        buttons.add(index, button);
        return this;
    }

    @Override
    public ControlBar.Builder addButton(InventoryPosition position, Button button) {
        buttons.add(position.getIndex(), button);
        return this;
    }

    @Override
    public ControlBar.Builder addButtons(List<Button> buttons) {
        this.buttons.addAll(buttons);
        return this;
    }

    @Override
    public ControlBar build() {
        return new ControlBar(buttons) {
            @Override
            public void update() {
                // TODO implement
            }
        };
    }
}