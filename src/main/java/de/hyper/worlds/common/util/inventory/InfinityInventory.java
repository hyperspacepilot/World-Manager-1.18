package de.hyper.worlds.common.util.inventory;

import de.hyper.worlds.common.util.inventory.buttons.Button;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
public abstract class InfinityInventory<T> extends Inventory {

    protected List<T> list;
    protected int currentPage;
    protected double maxPage;

    public InfinityInventory(String title, int rows, boolean closeable) {
        super(title, rows, closeable);
    }

    public void registerNextPageButton(int row, int slot, ItemStack itemStack) {
        registerButton(row, slot, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                currentPage++;
                cleanInventory();
                fillInventory();
            }
        }, itemStack);
    }

    public void registerLastPageButton(int row, int slot, ItemStack itemStack) {
        registerButton(row, slot, new Button() {
            @Override
            public void onClick(InventoryAction inventoryAction) {
                currentPage--;
                cleanInventory();
                fillInventory();
            }
        }, itemStack);
    }
}