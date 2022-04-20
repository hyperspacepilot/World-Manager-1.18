package de.hyper.worlds.common.util.minventorry.buttons;

import lombok.AllArgsConstructor;
import org.bukkit.event.inventory.InventoryAction;

@AllArgsConstructor
public abstract class NextPageButton extends Button {

    private int page;
    private double maxPage;

    @Override
    public void onClick(InventoryAction event) {
        if (page < maxPage) {
            nextPage();
        }
    }

    public abstract void nextPage();
}