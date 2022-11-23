package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class BlockFadeSetting extends WorldSetting {

    public BlockFadeSetting() {
        super(SettingType.BLOCK_FADE,
                new SettingState(
                        b("blockfade.off", "false"),
                        b("blockfade.on", "true")),
                "Block-Fade", "settings.blockfade.desc", Material.BUBBLE_CORAL, false);
    }
}
