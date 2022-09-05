package de.hyper.worlds.common.util.inventory.designs;

import de.hyper.worlds.common.util.inventory.InventoryDesign;
import org.bukkit.inventory.ItemStack;

public class CleanBackGroundDesign extends InventoryDesign {

    protected ItemStack backGroundMaterial;

    public CleanBackGroundDesign(int rows, ItemStack backGroundMaterial) {
        super(rows);
        this.backGroundMaterial = backGroundMaterial;
    }

    @Override
    public ItemStack[][] getLines() {
        for (int a = 0; a < rows; a++) {
            for (int b = 0; b < 9; b++) {
                lines[a][b] = backGroundMaterial;
            }
        }
        return lines;
    }
}
