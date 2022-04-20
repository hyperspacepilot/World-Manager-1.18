package de.hyper.worlds.common.util.minventorry;

import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.minventorry.buttons.Button;
import de.hyper.worlds.common.util.minventorry.designs.TopandBottomDesign;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public abstract class ChooseMinventorry<T> extends Minventorry {

    private List<T> list;

    public ChooseMinventorry(int rows, List<T> list) {
        super("Choose", rows);
        this.list = list;
        setCloseable(false);
    }

    public void buildInventory() {
        setDesign(new TopandBottomDesign(GlassPane.C7, null));
        int row = 2;
        int slot = 0;
        for (T t : list) {
            setSlot(row, slot, new Button() {
                @Override
                public void onClick(InventoryAction event) {
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
    }

    public abstract void selected(T t);
    public abstract ItemStack buildItem(T t);
}
