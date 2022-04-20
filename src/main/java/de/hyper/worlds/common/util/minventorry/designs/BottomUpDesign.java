package de.hyper.worlds.common.util.minventorry.designs;

import org.bukkit.inventory.ItemStack;

public class BottomUpDesign extends Design {

	ItemStack boarder, bg;
	
	public BottomUpDesign(ItemStack boarder, ItemStack bg) {
		this.boarder = boarder;
		this.bg = bg;
	}
	@Override
	public ItemStack setSlot(int row, int slot, int maxrows) {
		return (row == 1 || row == maxrows-- ? boarder : bg);
	}

}
