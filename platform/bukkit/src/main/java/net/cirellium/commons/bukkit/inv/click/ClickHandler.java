package net.cirellium.commons.bukkit.inv.click;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.cirellium.commons.bukkit.inv.button.Button;
import net.cirellium.commons.bukkit.inv.click.ClickHandler.ClickInformation;
import net.cirellium.commons.common.util.Processor;

/**
 * ClickHandler is an interface that handles click actions which is used in the {@link Button} class.
 * 
 * It handles the click action using the {@link ClickInformation} record and returns a boolean value that indicates wether the click action was successful or not.
 * 
 * Therefor it implements the {@link Processor} interface.
 * 
 * @author Fear
 * @see Button
 * @see Processor
 */
public interface ClickHandler extends Processor<ClickInformation, ClickResponse> {
    
    /**
     * Executes the click action.
     * 
     * @param clickInformation The click information
     * @return The click response
     */
    ClickResponse click(ClickInformation clickInformation);

    /**
     * Processes the click action by calling the {@link #click(ClickInformation)} method.
     */
    @Override
    default ClickResponse process(ClickInformation clickInformation) {
        return click(clickInformation);
    }

    /**
     * ClickInformation is a simple record that contains information about a click.
     * 
     * It contains the click type and the slot of the click.
     * 
     * @author Fear
     */
    public record ClickInformation(Player player, ClickType type, int slot) {
        /**
         * Creates a new ClickInformation record from an {@link InventoryClickEvent}.
         * 
         * @param event The event to create the record from
         */
        public ClickInformation(InventoryClickEvent event) {
            this((Player) event.getWhoClicked(), event.getClick(), event.getSlot());
        }
    }
}