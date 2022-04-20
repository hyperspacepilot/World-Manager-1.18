package de.hyper.worlds.common.util.minventorry.designs;

import org.bukkit.inventory.ItemStack;

public class BottomDesign extends Design {

	ItemStack boarder, bg;
	
	public BottomDesign(ItemStack boarder, ItemStack bg) {
		this.boarder = boarder;
		this.bg = bg;
	}
	
	@Override
	public ItemStack setSlot(int row, int slot, int maxrows) {
		return (row == maxrows ? boarder : bg);
	}

}
