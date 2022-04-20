package de.hyper.worlds.common.util.minventorry.designs;

import org.bukkit.inventory.ItemStack;

public class DoubleRingDesign extends Design {

	ItemStack boader1, boader2,bg;

	public DoubleRingDesign(ItemStack boader1, ItemStack boader2,ItemStack bg) {
		this.boader1 = boader1;
		this.boader2 = boader2;
		this.bg = bg;
	}

	@Override
	public ItemStack setSlot(int row, int slot, int maxrows) {
		if (row == 1 || row == maxrows) {
			return boader1;
		}
		if (slot == 0) {
			return boader1;
		}
		if (slot == 8) {
			return boader1;
		}
		if (slot == 1) {
			return boader2;
		}
		if (slot == 7) {
			return boader2;
		}
		if ((row == 2 || row == maxrows - 1) && slot != 1 && slot != 8) {
			return boader2;
		}
		return bg;
	}
}