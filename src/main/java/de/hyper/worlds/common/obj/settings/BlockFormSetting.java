package de.hyper.worlds.common.obj.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.SettingState;
import de.hyper.worlds.common.obj.WorldSetting;
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
