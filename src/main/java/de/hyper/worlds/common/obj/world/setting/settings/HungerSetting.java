package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
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