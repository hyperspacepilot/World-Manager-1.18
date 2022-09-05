package de.hyper.worlds.common.util.inventory.designs;

import de.hyper.worlds.common.util.inventory.InventoryDesign;
import org.bukkit.inventory.ItemStack;

public class SquaredBackGroundDesign extends InventoryDesign {

    protected ItemStack firstBackGroundItemStack;
    protected ItemStack secondBackGroundItemStack;

    public SquaredBackGroundDesign(int rows, ItemStack firstBackGroundItemStack, ItemStack secondBackGroundItemStack) {
        super(rows);
        this.firstBackGroundItemStack = firstBackGroundItemStack;
        this.secondBackGroundItemStack = secondBackGroundItemStack;
    }

    @Override
    public ItemStack[][] getLines() {
        for (int a = 0; a < rows; a++) {
            for (int b = 0; b <= 8; b++) {
                lines[a][b] = ((a % 2 == 0 && b % 2 == 0)
                        || (a % 2 == 1 && b % 2 == 1)) ? firstBackGroundItemStack : secondBackGroundItemStack;
            }
        }
        return lines;
    }
}
