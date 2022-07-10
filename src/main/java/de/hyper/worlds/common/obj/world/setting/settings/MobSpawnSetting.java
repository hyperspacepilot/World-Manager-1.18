package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class MobSpawnSetting extends WorldSetting {

    public MobSpawnSetting() {
        super(SettingType.MOB_SPAWNING,
                new SettingState(
                        b("mobspawn.off","false"),
                        b("mobspawn.on","true")),
                "Mob-Spawn", "settings.mobspawn.desc", Material.CREEPER_SPAWN_EGG, false);
    }
}