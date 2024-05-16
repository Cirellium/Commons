package net.cirellium.commons.bukkit.inv.implementation.special;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.entity.Player;

import net.cirellium.commons.bukkit.inv.InventoryBase;
import net.cirellium.commons.bukkit.inv.content.InventoryContent;
import net.cirellium.commons.bukkit.inv.content.InventoryDesign.InventorySize;

public abstract class PaginatedInventory extends InventoryBase {

    protected final Map<Integer, InventoryContent> pages;

    protected int currentPage;

    protected Consumer<PaginatedInventory> pageChangeAction;

    public PaginatedInventory(InventorySize size, String title, List<InventoryContent> pages) {
        this(size.getSize(), title, pages);
    }

    public PaginatedInventory(int size, String title, List<InventoryContent> pages) {
        super(size, title, pages.get(0));

        this.pages = new HashMap<>();

        for (int i = 0; i < pages.size(); i++) {
            this.pages.put(i, pages.get(i));
        }
    }

    public void setPageChangeAction(Consumer<PaginatedInventory> pageChangeAction) {
        this.pageChangeAction = pageChangeAction;
    }

    public Consumer<PaginatedInventory> getPageChangeAction() {
        return pageChangeAction;
    }

    public void setPage(int page, InventoryContent content) {
        pages.put(page, content);
    }

    public InventoryContent getPage(int page) {
        return pages.get(page) != null ? pages.get(page) : new InventoryContent(size);
    }

    public void changePage(int newPage) {
        if (newPage < 0 || newPage > pages.size()) {
            return;
        }

        this.content = getPage(currentPage);
        ((Player) this.inventory.getViewers().get(0)).updateInventory();
        super.updateInventory();

        pageChangeAction.accept(this);
    }

    public void nextPage() {
        changePage(currentPage++);
    }

    public void previousPage() {
        changePage(--currentPage);
    }
    
    
}