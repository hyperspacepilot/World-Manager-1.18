package de.hyper.worlds.common.util.minventorry.designs;

import org.bukkit.inventory.ItemStack;

public class SquaredBackground extends Design {
	
	ItemStack is1, is2;

	public SquaredBackground(ItemStack is1, ItemStack is2) {
		this.is1 = is1;
		this.is2 = is2;
	}

	@Override
	public ItemStack setSlot(int row, int slot, int maxrows) {
		return (row+slot)%2==1 ? is1 : is2;
	}
}