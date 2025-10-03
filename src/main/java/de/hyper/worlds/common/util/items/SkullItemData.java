package de.hyper.worlds.common.util.items;

public class SkullItemData extends de.hyper.inventory.items.SkullItemData {

    public SkullItemData(HDBSkulls hdbSkull) {
        super();
        set(hdbSkull);
    }

    public de.hyper.inventory.items.SkullItemData set(HDBSkulls hdbSkull) {
        this.setSkull(hdbSkull.getValue(), hdbSkull.getSkullSkinURL());
        return this;
    }
}
