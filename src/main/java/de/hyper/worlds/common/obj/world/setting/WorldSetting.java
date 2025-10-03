package de.hyper.worlds.common.obj.world.setting;

import de.hyper.inventory.items.ItemData;
import de.hyper.inventory.items.SimpleItemData;
import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.domain.WorldManagement;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;

@Getter
public class WorldSetting {

    private final SettingType type;
    private final SettingState state;
    private final String name;
    private final String descKey;
    private final Material material;
    private final boolean adminSetting;

    public WorldSetting(SettingType type, SettingState state, String name, String descKey,
                        Material material, boolean adminSetting) {
        this.type = type;
        this.state = state;
        this.name = name;
        this.descKey = descKey;
        this.material = material;
        this.adminSetting = adminSetting;
    }

    public static StatePart b(String nameKey, String value) {
        return new StatePart(nameKey, value);
    }

    public void change(InventoryAction action) {
        state.change(action);
    }

    public void buildItemLore(ItemData itemData) {
        itemData.addLore(WorldManagement.get().getLanguage().getText(this.descKey));
        itemData.addLore(" ");
        int a = 2;
        int b = 0;
        for (StatePart statePart : state.getStateParts()) {
            itemData.addLore((b == state.getActiveAsInt() ? "§7➙ " : "    ") + (WorldManagement.get().getLanguage().getText(statePart.getNameKey())));
            a++;
            b++;
        }
        itemData.addLore(" ");
        itemData.addLore(WorldManagement.get().getLanguage().getText("settings.general.leftclick"));
        itemData.addLore(WorldManagement.get().getLanguage().getText("settings.general.rightclick"));
        if (adminSetting) {
            itemData.addLore(" ");
            itemData.addLore(WorldManagement.get().getLanguage().getText("settings.general.adminsetting"));
        }
    }

    public ItemData buildDisplayItem() {
        ItemData itemData = new SimpleItemData(material).setDisplayName("§b" + this.name);
        buildItemLore(itemData);
        return itemData;
    }
}