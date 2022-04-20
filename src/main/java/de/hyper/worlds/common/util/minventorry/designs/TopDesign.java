package de.hyper.worlds.common.util.minventorry.designs;

import org.bukkit.inventory.ItemStack;

public class TopDesign extends Design {

	ItemStack bg, boarder;
	
	public TopDesign(ItemStack boarder, ItemStack bg) {
		this.boarder = boarder;
		this.bg = bg;
	}

	@Override
	public ItemStack setSlot(int row, int slot, int maxrows) {
		return (row == 1 ? boarder : bg);
	}
}