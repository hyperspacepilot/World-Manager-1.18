package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class MobDropsSetting extends WorldSetting {

    public MobDropsSetting() {
        super(SettingType.MOB_DROPS,
                new SettingState(
                        b("mobdrops.off","false"),
                        b("mobdrops.on","true")),
                "Mob-Drops", "settings.mobdrops.desc", Material.BONE, false);
    }
}
