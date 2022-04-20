package de.hyper.worlds.common.obj.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.SettingState;
import de.hyper.worlds.common.obj.WorldSetting;
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
