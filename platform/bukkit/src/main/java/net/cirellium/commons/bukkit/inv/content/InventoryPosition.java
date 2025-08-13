package net.cirellium.commons.bukkit.inv.content;

import net.cirellium.commons.bukkit.inv.content.InventoryDesign.InventorySize;

/**
 * Represents a position in an inventory.
 *
 * This record provides a convenient way to represent a position in an inventory
 * using a column and row. It also provides methods to get the slot index and
 * create an instance from a slot index.
 *
 * @author Fear
 */
public record InventoryPosition(Column column, Row row) {

    /**
     * Returns the slot index of the position.
     *
     * @return The slot index of the position.
     */
    public int getSlot() {
        return row.getStartingIndex() + column.getColumn();
    }

    /**
     * Represents a column in an inventory.
     *
     * @author Fear
     */
    public enum Column {
        /**
         * The first column.
         */
        ONE(0),
        /**
         * The second column.
         */
        TWO(1),
        /**
         * The third column.
         */
        THREE(2),
        /**
         * The fourth column.
         */
        FOUR(3),
        /**
         * The fifth column.
         */
        FIVE(4),
        /**
         * The sixth column.
         */
        SIX(5),
        /**
         * The seventh column.
         */
        SEVEN(6),
        /**
         * The eighth column.
         */
        EIGHT(7),
        /**
         * The ninth column.
         */
        NINE(8);

        private final int column;

        /**
         * Constructs a new column.
         *
         * @param column The column index.
         */
        private Column(int column) {
            this.column = column;
        }

        /**
         * Returns the column index.
         *
         * @return The column index.
         */
        public int getColumn() {
            return column;
        }
    }

    /**
     * Represents a row in an inventory.
     *
     * @author Fear
     */
    public enum Row {
        /**
         * The first row.
         */
        ONE(0),
        /**
         * The second row.
         */
        TWO(9),
        /**
         * The third row.
         */
        THREE(18),
        /**
         * The fourth row.
         */
        FOUR(27),
        /**
         * The fifth row.
         */
        FIVE(36),
        /**
         * The sixth row.
         */
        SIX(45);

        private final int startingIndex;

        /**
         * Constructs a new row.
         *
         * @param row The row index.
         */
        private Row(int row) {
            this.startingIndex = row;
        }

        /**
         * Returns the starting index of the row.
         *
         * @return The starting index of the row.
         */
        public int getStartingIndex() {
            return startingIndex;
        }
    }

    /**
     * Creates a new {@link InventoryPosition} from a slot index.
     *
     * @param i The slot index.
     * @return The {@link InventoryPosition} corresponding to the slot index.
     * @throws IllegalArgumentException If the slot index is invalid.
     */
    public static InventoryPosition of(int i) {
        if (i < 0 || i > 53) {
            throw new IllegalArgumentException("The slot must be a valit inventory slot between 0 and 53");
        }
        return new InventoryPosition(Column.values()[i % 9], Row.values()[i / 9]);
    }

    /**
     * Creates a new {@link InventoryPosition} from an {@link InventorySize} and a slot index.
     *
     * @param size The {@link InventorySize} of the inventory.
     * @param i The slot index.
     * @return The {@link InventoryPosition} corresponding to the slot index.
     * @throws IllegalArgumentException If the slot index is invalid.
     */
    public static InventoryPosition of(InventorySize size, int i) {
        if (i < 0 || i >= size.getSize()) {
            throw new IllegalArgumentException("The slot must be a valid inventory slot between 0 and " + (size.getSize() - 1));
        }
        return new InventoryPosition(Column.values()[i % 9], Row.values()[i / 9]);
    }

    /**
     * Returns the index of the position.
     *
     * @return The index of the position.
     */
    public int getIndex() {
        return row.getStartingIndex() + column.getColumn();
    }
}