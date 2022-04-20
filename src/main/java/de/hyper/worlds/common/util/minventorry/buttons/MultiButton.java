package de.hyper.worlds.common.util.minventorry.buttons;

import org.bukkit.event.inventory.InventoryAction;

public class MultiButton extends Button {
	
	Button[] buttons;
	
	public MultiButton(Button... buttons) {
		this.buttons = buttons;
	}
	
	@Override
	public void onClick(InventoryAction action) {
		for (Button c : buttons) c.onClick(action);
	}
	
	@Override
	public void onAdd() {
		for (Button c : buttons) c.add(minv);
	}
}
