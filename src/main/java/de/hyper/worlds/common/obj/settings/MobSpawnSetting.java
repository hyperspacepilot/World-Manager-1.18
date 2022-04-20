package de.hyper.worlds.common.obj.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.SettingState;
import de.hyper.worlds.common.obj.WorldSetting;
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