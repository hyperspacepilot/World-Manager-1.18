package de.hyper.worlds.common.util.minventorry.designs;

import org.bukkit.inventory.ItemStack;

public class TableDesign extends Design {

	ItemStack bg, boarder;
	
	public TableDesign(ItemStack boarder, ItemStack bg) {
		this.boarder = boarder;
		this.bg = bg;
	}
	
	@Override
	public ItemStack setSlot(int row, int slot, int maxrows) {
		return (slot == 0 || slot == 2 || slot == 4 || slot == 6 || slot == 8 || row ==  1 || row == maxrows ? boarder : bg);
	}
}