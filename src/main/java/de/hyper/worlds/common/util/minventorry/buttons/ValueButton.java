package de.hyper.worlds.common.util.minventorry.buttons;

import java.util.function.BiConsumer;

import org.bukkit.event.inventory.InventoryAction;

public abstract class ValueButton extends Button {
	
	BiConsumer<?, ?> consumer;
	
	public ValueButton(BiConsumer<?, ?> consumer) {
		this.consumer = consumer;
	}

	@Override
	public final void onClick(InventoryAction action) {
		onClick(consumer);
	}
	
	public abstract void onClick(BiConsumer<?, ?> consumer);
}
