package de.hyper.worlds.common.util.inventory;

import de.hyper.worlds.common.util.inventory.buttons.Button;
import de.hyper.worlds.common.util.inventory.designs.TopBottomLineBackGroundDesign;
import de.hyper.worlds.common.util.items.GlassPane;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class ChooseInventory<T> extends Inventory {

    private List<T> list;

    public ChooseInventory(int rows, List<T> list) {
        super("Choose", rows, false);
        this.setDesign(new TopBottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.list = list;
    }

    @Override
    public Inventory fillInventory() {
        int row = 2;
        int slot = 0;
        for (T t : list) {
            registerButton(row, slot, new Button() {
                @Override
                public void onClick(InventoryAction inventoryAction) {
                    setCloseable(true);
                    selected(t);
                }
            }, buildItem(t));
            slot++;
            if (slot > 8) {
                row++;
                slot = 0;
            }
        }
        return this;
    }

    @Override
    public Inventory cleanInventory() {
        return this;
    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }

    public abstract void selected(T t);

    public abstract ItemStack buildItem(T t);
}
