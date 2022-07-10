package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class PotionSetting extends WorldSetting {

    public PotionSetting() {
        super(SettingType.POTION,
                new SettingState(
                        b("potion.off", "false"),
                        b("potion.on", "true")),
                "Potions", "settings.potion.desc", Material.POTION, false);
    }
}
