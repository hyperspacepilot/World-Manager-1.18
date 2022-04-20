package de.hyper.worlds.common.util.minventorry.designs;

import org.bukkit.inventory.ItemStack;

public class BoarderTest extends Design {
	
	ItemStack is1, is2, is3;

	public BoarderTest(ItemStack is1, ItemStack is2, ItemStack is3) {
		this.is1 = is1;
		this.is2 = is2;
		this.is3 = is3;
	}

	@Override
	public ItemStack setSlot(int row, int slot, int maxrows) {
		if (row == 1 || row == maxrows) {
			if (slot == 2 || slot == 6) return is2;
			return is1;
		}
		if (row == 2 || row == maxrows-1) {
			if (slot == 2 || slot == 6) return is1;
		}
		if (slot == 0 || slot == 8) return is1;
		return is3;
	}
}
