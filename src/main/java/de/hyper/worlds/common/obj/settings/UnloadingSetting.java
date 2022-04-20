package de.hyper.worlds.common.obj.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.SettingState;
import de.hyper.worlds.common.obj.WorldSetting;
import de.hyper.worlds.common.util.items.ItemBuilder;
import org.bukkit.Material;

public class UnloadingSetting extends WorldSetting {

    public UnloadingSetting() {
        super(SettingType.UNLOADING,
                new SettingState(
                        b("unloading.on","true"),
                        b("unloading.off","false")),
                "Unloading", "settings.unloading.desc", Material.CHEST_MINECART, true);
    }
}