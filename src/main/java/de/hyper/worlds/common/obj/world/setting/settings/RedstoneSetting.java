package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class RedstoneSetting extends WorldSetting {

    public RedstoneSetting() {
        super(SettingType.REDSTONE,
                new SettingState(
                        b("redstone.on","true"),
                        b("redstone.off","false")),
                "Redstone", "settings.redstone.desc", Material.REDSTONE, false);
    }
}