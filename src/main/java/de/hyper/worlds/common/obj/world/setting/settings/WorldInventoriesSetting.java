package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class WorldInventoriesSetting extends WorldSetting {

    public WorldInventoriesSetting() {
        super(SettingType.WORLDINVENTORIES,
                new SettingState(
                        b("worldinventories.off", "false"),
                        b("worldinventories.on", "true")),
                "World-Inventories", "settings.worldinventories.desc", Material.CHEST, false);
    }
}
