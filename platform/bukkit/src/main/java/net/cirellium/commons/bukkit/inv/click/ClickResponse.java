package net.cirellium.commons.bukkit.inv.click;

import java.util.function.Predicate;

import net.cirellium.commons.bukkit.inv.InventoryBase;
import net.cirellium.commons.bukkit.inv.click.ClickHandler.ClickInformation;
import net.cirellium.commons.common.message.Message;
import net.cirellium.commons.common.util.Processor;

/**
 * This interface represents a response to a handled click on a button.
 * 
 * @author Fear
 * @see ClickHandler
 * @see Processor
 */
public interface ClickResponse extends Processor<ClickInformation, Boolean> {
    
    /**
     * Handles the click response and returns whether it succeeded.
     * 
     * @param clickInformation The click information
     * @return Whether the click was successful
     */
    boolean handleClickResponse(ClickInformation clickInformation);

    /**
     * Processes the click response by calling the {@link #handleClickResponse(ClickInformation)} method.
     * 
     * @param clickInformation The click information
     * @return Whether the click was successful
     */
    @Override
    default Boolean process(ClickInformation clickInformation) {
        return handleClickResponse(clickInformation);
    }

    /**
     * Sends a message to the player.
     * 
     * @param message The message to send
     * @return The ClickResponse object
     */
    static ClickResponse sendMessage(Message message) {
        return sendMessage(message.getString());
    }

    /**
     * Sends a message to the player.
     * 
     * @param message The message to send
     * @return The ClickResponse object
     */
    static ClickResponse sendMessage(String message) {
        return (clickInformation) -> {
            clickInformation.player().sendMessage(message);
            return true;
        };
    }

    /**
     * Opens an inventory for the player.
     * 
     * @param inventory The inventory to open
     * @return The ClickResponse object
     */
    static ClickResponse openInventory(InventoryBase inventory) {
        return (clickInformation) -> {
            inventory.openInventory(clickInformation.player());
            return true;
        };
    }

    /**
     * Runs a command as the player.
     * 
     * @param command The command to run
     * @return The ClickResponse object
     */
    static ClickResponse runCommand(String command) {
        return (clickInformation) -> clickInformation.player().performCommand(command);
    }

    /**
     * Closes the inventory of the player.
     * 
     * @return The ClickResponse object
     */
    static ClickResponse closeInventory() {
        return (clickInformation) -> {
            clickInformation.player().closeInventory();
            return true;
        };
    }

    /**
     * Called if a click was handled successfully, does nothing.
     * 
     * @return The ClickResponse object
     */
    static ClickResponse success() { return (clickInformation) -> true; }

    /**
     * Returns the value of a {@link Predicate}.
     * 
     * @param predicate The predicate to evaluate
     * @return The ClickResponse object
     */
    static ClickResponse successIf(Predicate<ClickInformation> predicate) {
        return (clickInformation) -> predicate.test(clickInformation);
    }
}