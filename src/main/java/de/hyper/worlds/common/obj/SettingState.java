package de.hyper.worlds.common.obj;

import lombok.Getter;
import org.bukkit.event.inventory.InventoryAction;

public class SettingState {

    private int active;
    @Getter private StatePart[] stateParts;

    public SettingState(StatePart... stateParts) {
        this.active = 0;
        this.stateParts = stateParts;
    }

    public void change(InventoryAction action) {
        if (action == InventoryAction.PICKUP_ALL) {
            next();
            return;
        }
        last();
    }

    public int getActiveAsInt() {
        return active;
    }

    public StatePart getActive() {
        return stateParts[active];
    }

    public void next() {
        int max = stateParts.length -1;
        if (active < max) {
            active++;
        } else {
            active = 0;
        }
    }

    public void last() {
        int max = stateParts.length -1;
        if (active > 0) {
            active--;
        } else {
            active = max;
        }
    }
}