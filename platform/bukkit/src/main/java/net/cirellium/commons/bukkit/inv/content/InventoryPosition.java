package net.cirellium.commons.bukkit.inv.content;

public record InventoryPosition(Column column, Row row) {

    public int getSlot() {
        return row.getStartingIndex() + column.getColumn();
    }
    
    public enum Column {
        ONE(0),
        TWO(1),
        THREE(2),
        FOUR(3),
        FIVE(4),
        SIX(5),
        SEVEN(6),
        EIGHT(7),
        NINE(8);
        
        private int column;
        
        private Column(int column) {
            this.column = column;
        }
        
        public int getColumn() {
            return column;
        }
    }

    public enum Row {
        ONE(0),
        TWO(9),
        THREE(18),
        FOUR(27),
        FIVE(36),
        SIX(45);
        
        private int startingIndex;
        
        private Row(int row) {
            this.startingIndex = row;
        }
        
        public int getStartingIndex() {
            return startingIndex;
        }
    }

    public static InventoryPosition of(int i) {
        if (i < 0 || i > 53) {
            throw new IllegalArgumentException("The slot must be a valit inventory slot between 0 and 53");
        }
        return new InventoryPosition(Column.values()[i % 9], Row.values()[i / 9]);
    }
}