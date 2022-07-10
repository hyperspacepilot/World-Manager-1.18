package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class BlockFormSetting extends WorldSetting {

    public BlockFormSetting() {
        super(SettingType.BLOCK_FORM,
                new SettingState(
                        b("blockform.off","false"),
                        b("blockform.on","true")),
                "Block-Form", "settings.blockform.desc", Material.OBSIDIAN, false);
    }
}
