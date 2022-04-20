package de.hyper.worlds.common.util.minventorry.buttons;

import org.bukkit.event.inventory.InventoryAction;

public class ChangeRowsButton extends Button {
	
	int rows;
	
	public ChangeRowsButton(int rows) {
		this.rows = rows;
	}

	@Override
	public void onClick(InventoryAction action) {
		minv.setRows(rows);
	}
}
