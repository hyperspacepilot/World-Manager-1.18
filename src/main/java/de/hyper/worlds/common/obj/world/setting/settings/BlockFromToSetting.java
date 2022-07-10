package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class BlockFromToSetting extends WorldSetting {

    public BlockFromToSetting() {
        super(SettingType.BLOCK_FROM_TO,
                new SettingState(
                        b("blockfromto.off","false"),
                        b("blockfromto.on","true")),
                "Block-From-To", "settings.blockfromto.desc", Material.WATER_BUCKET, false);
    }
}
