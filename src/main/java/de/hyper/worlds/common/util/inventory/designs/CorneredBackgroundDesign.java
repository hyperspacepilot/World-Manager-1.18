package de.hyper.worlds.common.util.inventory.designs;

import de.hyper.worlds.common.util.inventory.InventoryDesign;
import org.bukkit.inventory.ItemStack;

public class CorneredBackgroundDesign extends InventoryDesign {

    protected ItemStack backGroundItemStack;
    protected ItemStack inCornerBackGroundItemStack;
    protected ItemStack outCornerBackGroundItemStack;

    public CorneredBackgroundDesign(int rows, ItemStack backGroundItemStack, ItemStack inCornerBackGroundItemStack,
                                    ItemStack outCornerBackGroundItemStack) {
        super((rows < 5 ? 5 : (rows > 6 ? 6 : rows)));
        this.backGroundItemStack = backGroundItemStack;
        this.inCornerBackGroundItemStack = inCornerBackGroundItemStack;
        this.outCornerBackGroundItemStack = outCornerBackGroundItemStack;
    }

    @Override
    public ItemStack[][] getLines() {
        for (int a = 0; a < rows; a++) {
            for (int b = 0; b <= 8; b++) {
                lines[a][b] = (((a == 0 &&
                        (b == 0 || b == 1 || b == 7 || b == 8))
                        || (a == 1 &&
                        (b == 0 || b == 8)) ||
                        (a == (rows - 2)
                                && (b == 0 || b == 8))
                        || (a == (rows - 1)
                        && (b == 0 || b == 1 || b == 7 || b == 8))) ?
                        ((((a == 0 || a == (rows - 1))
                                && (b == 0 || b == 8))) ? inCornerBackGroundItemStack : outCornerBackGroundItemStack)
                        : backGroundItemStack);
            }
        }
        return lines;
    }
}
