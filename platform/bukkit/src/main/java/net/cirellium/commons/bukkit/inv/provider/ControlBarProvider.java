package net.cirellium.commons.bukkit.inv.provider;

import java.util.Map;

import org.bukkit.Material;

import net.cirellium.commons.bukkit.inv.bar.ControlBar;
import net.cirellium.commons.bukkit.inv.bar.implementation.ControlBarBuilder;
import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.button.implementation.ButtonBuilder;
import net.cirellium.commons.bukkit.inv.button.implementation.ItemBuilder;
import net.cirellium.commons.bukkit.inv.content.InventoryDesign.InventorySize;
import net.cirellium.commons.bukkit.inv.content.InventoryPosition;
import net.cirellium.commons.common.util.Provider;

public interface ControlBarProvider extends Provider<ControlBar, Map<InventoryPosition, Button>> {

    @Override
    ControlBar provide(Map<InventoryPosition, Button> map);

    ControlBarProvider DEFAULT = new ControlBarProvider() {
        @Override
        public ControlBar provide(Map<InventoryPosition, Button> map) {
            return new ControlBarBuilder()
                    .addButton(InventoryPosition.of(InventorySize.ONE_ROW, 0), new ButtonBuilder()
                            .itemBuilder(new ItemBuilder(Material.ARROW)
                                    .displayName("Zurück"))
                            .slot(0)
                            .build())
                    .addButton(InventoryPosition.of(InventorySize.ONE_ROW, 8), new ButtonBuilder()
                            .itemBuilder(new ItemBuilder(Material.ARROW)
                                    .displayName("Weiter"))
                            .slot(8)
                            .build())
                    .addButtons(map.values().stream().filter(button -> button.slot() != 0 && button.slot() != 8).toList())
                    .build();
        }
    };

}