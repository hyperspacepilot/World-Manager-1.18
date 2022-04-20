package de.hyper.worlds.common.obj.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.SettingState;
import de.hyper.worlds.common.obj.WorldSetting;
import org.bukkit.Material;

public class HungerSetting extends WorldSetting {

    public HungerSetting() {
        super(SettingType.HUNGER,
                new SettingState(
                        b("hunger.on","true"),
                        b("hunger.off","false")),
                "Hunger", "settings.hunger.desc", Material.BREAD, false);
    }
}