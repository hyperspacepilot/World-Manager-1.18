package de.hyper.worlds.common.util.minventorry.designs;

import org.bukkit.inventory.ItemStack;

public class SideRightDesign extends Design {

	ItemStack boarder, bg;
	
	public SideRightDesign(ItemStack boarder, ItemStack bg) {
		this.boarder = boarder;
		this.bg = bg;
	}
	
	@Override
	public ItemStack setSlot(int row, int slot, int maxrows) {
		return (slot == 8 ? boarder : bg);
	}
}