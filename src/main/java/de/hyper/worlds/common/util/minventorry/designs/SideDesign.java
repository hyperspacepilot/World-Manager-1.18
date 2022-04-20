package de.hyper.worlds.common.util.minventorry.designs;

import org.bukkit.inventory.ItemStack;

public class SideDesign extends Design {

	ItemStack boarder, bg;
	
	public SideDesign(ItemStack boarder, ItemStack bg) {
		this.boarder = boarder;
		this.bg = bg;
	}
	
	@Override
	public ItemStack setSlot(int row, int slot, int maxrows) {
		return (slot == 0 || slot == 8 ? boarder : bg);
	}
}