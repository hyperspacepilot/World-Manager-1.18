package de.hyper.worlds.common.util.minventorry.buttons;

import de.hyper.worlds.common.util.minventorry.Minventorry;
import org.bukkit.event.inventory.InventoryAction;

public abstract class Button {
	
	protected Minventorry minv = null;

	public final void add(Minventorry minventorry) {
		minv = minventorry;
		onAdd();
	}
	
	public void onAdd() {}
	
	public void onClick(InventoryAction event) {
		
	}
}