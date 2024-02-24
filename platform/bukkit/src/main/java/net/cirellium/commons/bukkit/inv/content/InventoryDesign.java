package net.cirellium.commons.bukkit.inv.content;

import net.cirellium.commons.bukkit.inv.content.InventoryPosition.Row;

public record InventoryDesign(InventorySize size, String title, InventoryContent content) {

    public enum InventorySize {
        ONE_ROW(Row.ONE), TWO_ROWS(Row.TWO), THREE_ROWS(Row.THREE), FOUR_ROWS(Row.FOUR), FIVE_ROWS(Row.FIVE), SIX_ROWS(Row.SIX);

        private final Row rows;

        InventorySize(Row rows) {
            this.rows = rows;
        }

        public int getSize() {
            return rows.getStartingIndex() + 9;
        }
    }

    public interface Builder {
        Builder size(InventorySize size);

        Builder title(String title);

        Builder content(InventoryContent.Builder content);

        InventoryDesign build();
    }
}