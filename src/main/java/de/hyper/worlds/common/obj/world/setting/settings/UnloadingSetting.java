package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
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