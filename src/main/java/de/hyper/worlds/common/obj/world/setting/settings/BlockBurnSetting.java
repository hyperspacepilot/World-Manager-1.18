package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class BlockBurnSetting extends WorldSetting {

    public BlockBurnSetting() {
        super(SettingType.BLOCK_BURN,
                new SettingState(
                        b("blockburn.off","false"),
                        b("blockburn.on","true")),
                "Block-Burn", "settings.blockburn.desc", Material.FLINT_AND_STEEL, false);
    }
}
