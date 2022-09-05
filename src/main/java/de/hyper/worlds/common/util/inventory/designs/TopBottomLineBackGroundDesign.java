package de.hyper.worlds.common.util.inventory.designs;

import de.hyper.worlds.common.util.inventory.InventoryDesign;
import org.bukkit.inventory.ItemStack;

public class TopBottomLineBackGroundDesign extends InventoryDesign {

    protected ItemStack backGroundItemStack;
    protected ItemStack borderBackGroundItemStack;

    public TopBottomLineBackGroundDesign(int rows, ItemStack backGroundItemStack, ItemStack borderBackGroundItemStack) {
        super(rows);
        this.backGroundItemStack = backGroundItemStack;
        this.borderBackGroundItemStack = borderBackGroundItemStack;
    }

    @Override
    public ItemStack[][] getLines() {
        for (int a = 0; a < rows; a++) {
            for (int b = 0; b <= 8; b++) {
                lines[a][b] = (a == 0 || a == (rows - 1)) ? borderBackGroundItemStack : backGroundItemStack;
            }
        }
        return lines;
    }
}
