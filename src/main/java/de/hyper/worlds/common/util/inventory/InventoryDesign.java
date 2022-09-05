package de.hyper.worlds.common.util.inventory;

import org.bukkit.inventory.ItemStack;

public abstract class InventoryDesign {

    protected int rows;
    protected ItemStack[][] lines;

    public InventoryDesign(int rows) {
        this.rows = (rows < 1 ? 1 : (rows > 6 ? 6 : rows));
        this.lines = new ItemStack[rows][9];
    }

    public abstract ItemStack[][] getLines();
}