package de.hyper.worlds.common.obj.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.SettingState;
import de.hyper.worlds.common.obj.WorldSetting;
import org.bukkit.Material;

public class BlockSpreadSetting extends WorldSetting {

    public BlockSpreadSetting() {
        super(SettingType.BLOCK_SPREAD,
                new SettingState(
                        b("spreading.off","false"),
                        b("spreading.on","true")),
                "Block-Spread", "settings.spreading.desc", Material.RED_MUSHROOM, false);
    }
}
