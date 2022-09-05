package de.hyper.worlds.common.util.inventory;

import org.bukkit.event.inventory.InventoryAction;

public abstract class InventoryButton {

    protected Inventory inventory;

    public final void add(Inventory inventory) {
        this.inventory = inventory;
        onAdd();
    }

    public abstract void onClick(InventoryAction inventoryAction);

    public abstract void onAdd();
}
