package de.hyper.worlds.common.util.minventorry.designs;

import org.bukkit.inventory.ItemStack;

public class SimpleDesign extends Design {
	
	ItemStack bg, boader;
	
	public SimpleDesign(ItemStack boader, ItemStack bg) {
		this.boader = boader;
		this.bg = bg;
	}

	@Override
	public ItemStack setSlot(int row, int slot, int maxrows) {
		return (slot == 0 || slot == 8 || row == maxrows || row == 1 ? boader : bg);
	}
}