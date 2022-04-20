package de.hyper.worlds.common.util.minventorry.buttons;

import lombok.AllArgsConstructor;
import org.bukkit.event.inventory.InventoryAction;

@AllArgsConstructor
public abstract class LastPageButton extends Button {

    private int page;

    @Override
    public void onClick(InventoryAction event) {
        if (page > 0) {
            lastPage();
        }
    }

    public abstract void lastPage();
}
