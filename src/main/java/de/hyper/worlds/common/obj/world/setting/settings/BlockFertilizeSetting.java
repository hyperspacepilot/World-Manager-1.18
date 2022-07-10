package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class BlockFertilizeSetting extends WorldSetting {

    public BlockFertilizeSetting() {
        super(SettingType.BLOCK_FERTILIZE,
                new SettingState(
                        b("blockfertilize.off","false"),
                        b("blockfertilize.on","true")),
                "Block-Fertilize", "settings.blockfertilize.desc", Material.BONE_MEAL, false);
    }
}
