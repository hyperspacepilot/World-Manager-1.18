package de.hyper.worlds.common.obj.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.SettingState;
import de.hyper.worlds.common.obj.WorldSetting;
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
