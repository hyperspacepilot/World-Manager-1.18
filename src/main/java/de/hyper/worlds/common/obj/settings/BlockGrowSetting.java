package de.hyper.worlds.common.obj.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.SettingState;
import de.hyper.worlds.common.obj.WorldSetting;
import org.bukkit.Material;

public class BlockGrowSetting extends WorldSetting {

    public BlockGrowSetting() {
        super(SettingType.BLOCK_GROW,
                new SettingState(
                        b("blockgrow.off","false"),
                        b("blockgrow.on","true")),
                "Block-Grow", "settings.blockgrow.desc", Material.WHEAT, false);
    }
}
