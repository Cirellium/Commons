package net.cirellium.commons.bukkit.inv.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.content.InventoryPosition;
import net.cirellium.commons.common.util.Provider;

public interface ControlBarProvider extends Provider<List<Button>, Map<InventoryPosition, Button>> {
    
    @Override
    List<Button> provide(Map<InventoryPosition, Button> buttons);

    ControlBarProvider simpleControlBarProvider = (buttons) -> {
        List<Button> controlBar = new ArrayList<>(9);
        if (buttons.size() > 9) throw new IllegalArgumentException("The control bar can only have 9 buttons.");

        for (int i = 0; i < 9; i++) {
            Button button = buttons.get(InventoryPosition.of(i));
            if (button != null) {
                controlBar.add(button);
            } else {
                controlBar.add(ButtonProvider.backgroundButtonProvider.provide(i));
            }
        }
        return controlBar;
    };

}