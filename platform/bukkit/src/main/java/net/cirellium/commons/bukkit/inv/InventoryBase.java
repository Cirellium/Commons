package net.cirellium.commons.bukkit.inv;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.click.ClickHandler.ClickInformation;
import net.cirellium.commons.bukkit.inv.click.ClickResponse;
import net.cirellium.commons.bukkit.inv.content.InventoryContent;
import net.cirellium.commons.bukkit.inv.content.InventoryDesign.InventorySize;

/**
 * This class provides an implementation of the {@link CustomInventory} interface.
 * 
 * @author Fear
 */
public abstract class InventoryBase implements CustomInventory, InventoryHolder {

    protected final Inventory inventory;
    protected InventoryContent content;

    protected int size;

    protected Consumer<Player> closeAction;

    public InventoryBase(InventorySize size, String title, InventoryContent content) {
        this(size.getSize(), title, content);
    }

    public InventoryBase(int size, String title, InventoryContent content) {
        this.inventory = Bukkit.createInventory(this, size, title);
        this.content = content;
        this.size = size;

        this.closeAction = (p) -> {};

        // fillInventory();
        updateInventory();
    }

    public void fillInventory() {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, content.get(i).itemStack());
        }
    }

    public void updateInventory() {
        inventory.clear();

        for (int i = 0; i < size; i++) {
            inventory.setItem(i, content.get(i).itemStack());
        }
    }

    public void setCloseAction(Consumer<Player> closeAction) {
        this.closeAction = closeAction;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void openInventory(Player player) {
        player.closeInventory();
        this.updateInventory();
        player.openInventory(inventory);
        player.updateInventory();
    }

    @Override
    public void closeInventory(Player player) {
        closeAction.accept(player);
    }

    @Override
    public ClickResponse onClick(ClickInformation clickInformation, Button clicked) {
        
        Bukkit.getLogger().info("Returning clickhandler: " + clicked.clickHandler());
        return clicked.clickHandler().click(clickInformation);
    }

    @Override
    public InventoryContent getContent() {
        return content;
    }
}