package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class SizeSetting extends WorldSetting {

    public SizeSetting() {
        super(SettingType.SIZE,
                new SettingState(
                        b("size.small","500"),
                        b("size.normal","1000"),
                        b("size.big","2500"),
                        b("size.huge","5000"),
                        b("size.enormously","10000"),
                        b("size.immense","50000"),
                        b("size.gigantic","100000"),
                        b("size.oversized","1000000")),
                "Size", "settings.size.desc", Material.GRASS_BLOCK, true);
    }
}