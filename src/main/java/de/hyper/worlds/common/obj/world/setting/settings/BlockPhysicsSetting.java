package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class BlockPhysicsSetting extends WorldSetting {

    public BlockPhysicsSetting() {
        super(SettingType.BLOCK_PHYSICS,
                new SettingState(
                        b("blockphysics.off","false"),
                        b("blockphysics.on","true")),
                "Block-Physics", "settings.blockphysics.desc", Material.GLOW_INK_SAC, false);
    }
}