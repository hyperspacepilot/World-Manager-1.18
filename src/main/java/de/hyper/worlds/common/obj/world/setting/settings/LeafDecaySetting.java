package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class LeafDecaySetting extends WorldSetting {

    public LeafDecaySetting() {
        super(SettingType.LEAF_DECAY,
                new SettingState(
                        b("leafdecay.off","false"),
                        b("leafdecay.on","true")),
                "Leaf-Decay", "settings.leafdecay.desc", Material.OAK_LEAVES, false);
    }
}