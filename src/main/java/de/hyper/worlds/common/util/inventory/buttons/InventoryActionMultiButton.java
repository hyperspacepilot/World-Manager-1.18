package de.hyper.worlds.common.util.inventory.buttons;

import org.bukkit.event.inventory.InventoryAction;

import java.util.HashMap;

public class InventoryActionMultiButton extends MultiButton<InventoryAction> {

    public InventoryActionMultiButton() {
        super(new HashMap<>());
    }

    public InventoryActionMultiButton register(InventoryAction inventoryAction, Button button) {
        this.buttons.put(inventoryAction, button);
        return this;
    }

    @Override
    public void onClick(InventoryAction inventoryAction) {
        if (this.buttons.containsKey(inventoryAction)) {
            this.buttons.get(inventoryAction).onClick(inventoryAction);
        }
    }
}
