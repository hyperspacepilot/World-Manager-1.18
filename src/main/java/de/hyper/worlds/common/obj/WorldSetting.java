package de.hyper.worlds.common.obj;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor @Getter
public class WorldSetting {

    private SettingType type;
    private SettingState state;
    private String name;
    private String descKey;
    private Material material;
    private boolean adminSetting;

    public void change(InventoryAction action) {
        state.change(action);
    }

    public String[] buildItemLore() {
        String[] result = new String[state.getStateParts().length + (adminSetting ? 7 : 5)];
        result[0] = WorldManagement.get().getLanguage().getText(this.descKey);
        result[1] = " ";
        int a = 2;
        int b = 0;
        for (StatePart statePart : state.getStateParts()) {
            result[a] = (b == state.getActiveAsInt() ? "§7➙ " : "    ") + (WorldManagement.get().getLanguage().getText(statePart.getNameKey()));
            a++;
            b++;
        }
        result[a++] = "  ";
        result[a++] = WorldManagement.get().getLanguage().getText("settings.general.leftclick");
        result[a++] = WorldManagement.get().getLanguage().getText("settings.general.rightclick");
        if (adminSetting) {
            result[a++] = "   ";
            result[a++] = WorldManagement.get().getLanguage().getText("settings.general.adminsetting");
        }
        return result;
    }

    public ItemStack buildDisplayItem() {
        return new ItemBuilder(material).setDisplayName("§b" + this.name).setLore(buildItemLore()).getItem();
    }

    public static StatePart b(String nameKey, String value) {
        return new StatePart(nameKey, value);
    }
}