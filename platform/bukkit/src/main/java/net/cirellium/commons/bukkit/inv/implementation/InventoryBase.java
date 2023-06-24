package net.cirellium.commons.bukkit.inv.implementation;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import net.cirellium.commons.bukkit.inv.CustomInventory;
import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.click.ClickHandler.ClickInformation;
import net.cirellium.commons.bukkit.inv.click.ClickResponse;

/**
 * This class provides an implementation of the {@link CustomInventory} interface.
 * 
 * @author Fear
 */
public abstract class InventoryBase implements CustomInventory, InventoryHolder {

    protected final Inventory inventory;
    protected InventoryContent content;

    protected Consumer<Player> closeAction;

    public InventoryBase(int size, String title) {
        this.inventory = Bukkit.createInventory(null, size, title);
        this.content = new InventoryContent.Builder().empty().build();

        this.closeAction = (p) -> {};
    }

    public void updateInventory() {
        inventory.clear();

        for (Button button : content.getButtons().values()) {
            inventory.setItem(button.slot(), button.itemStack());
        }
    }

    public void setCloseAction(Consumer<Player> closeAction) {
        this.closeAction = closeAction;
    }

    @Override
    public void openInventory(Player player) {
        player.closeInventory();
        this.updateInventory();
        player.openInventory(inventory);
        // player.updateInventory();
    }

    @Override
    public void closeInventory(Player player) {
        player.closeInventory();

        closeAction.accept(player);
    }

    @Override
    public ClickResponse onClick(ClickInformation clickInformation, Button clicked) {
        return clicked.clickHandler().click(clickInformation);
    }

    @Override
    public InventoryContent getContent() {
        return content;
    }
}