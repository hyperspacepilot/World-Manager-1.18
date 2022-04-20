package de.hyper.worlds.common.util.minventorry.designs;

import de.hyper.worlds.common.util.minventorry.Minventorry;
import de.hyper.worlds.common.util.minventorry.buttons.NoButton;
import org.bukkit.inventory.ItemStack;

public class CornerDesign {

//	Requires 5 rows!
	
	public static void big(Minventorry inv, ItemStack inCorner, ItemStack middleCorner, ItemStack outCorner, ItemStack backGround) {

		inv.setDesign(new SimpleDesign(backGround, backGround));
		
		inv.setSlot(1, 0, new NoButton(), inCorner);
		inv.setSlot(1, 8, new NoButton(), inCorner);
		inv.setSlot(5, 0, new NoButton(), inCorner);
		inv.setSlot(5, 8, new NoButton(), inCorner);

		inv.setSlot(1, 1, new NoButton(), middleCorner);
		inv.setSlot(1, 7, new NoButton(), middleCorner);
		inv.setSlot(2, 0, new NoButton(), middleCorner);
		inv.setSlot(2, 8, new NoButton(), middleCorner);
		inv.setSlot(4, 0, new NoButton(), middleCorner);
		inv.setSlot(4, 8, new NoButton(), middleCorner);
		inv.setSlot(5, 1, new NoButton(), middleCorner);
		inv.setSlot(5, 7, new NoButton(), middleCorner);

		inv.setSlot(1, 2, new NoButton(), outCorner);
		inv.setSlot(1, 6, new NoButton(), outCorner);
		inv.setSlot(3, 0, new NoButton(), outCorner);
		inv.setSlot(3, 8, new NoButton(), outCorner);
		inv.setSlot(5, 2, new NoButton(), outCorner);
		inv.setSlot(5, 6, new NoButton(), outCorner);
	}
	
	public static void middle(Minventorry inv, ItemStack inCorner, ItemStack outCorner, ItemStack backGround) {
		inv.setDesign(new SimpleDesign(backGround, backGround));
		
		inv.setSlot(1, 0, new NoButton(), inCorner);
		inv.setSlot(1, 8, new NoButton(), inCorner);
		inv.setSlot(5, 0, new NoButton(), inCorner);
		inv.setSlot(5, 8, new NoButton(), inCorner);

		inv.setSlot(1, 1, new NoButton(), outCorner);
		inv.setSlot(1, 7, new NoButton(), outCorner);
		inv.setSlot(2, 0, new NoButton(), outCorner);
		inv.setSlot(2, 8, new NoButton(), outCorner);
		inv.setSlot(4, 0, new NoButton(), outCorner);
		inv.setSlot(4, 8, new NoButton(), outCorner);
		inv.setSlot(5, 1, new NoButton(), outCorner);
		inv.setSlot(5, 7, new NoButton(), outCorner);
	}
	
	public static void small(Minventorry inv, ItemStack corner, ItemStack backGround) {
		inv.setDesign(new SimpleDesign(backGround, backGround));
		
		inv.setSlot(1, 0, new NoButton(), corner);
		inv.setSlot(1, 8, new NoButton(), corner);
		inv.setSlot(5, 0, new NoButton(), corner);
		inv.setSlot(5, 8, new NoButton(), corner);
	}
}
