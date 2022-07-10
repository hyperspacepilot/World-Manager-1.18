package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class ExplosionSetting extends WorldSetting {

    public ExplosionSetting() {
        super(SettingType.EXPLOSION,
                new SettingState(
                        b("explosion.off","false"),
                        b("explosion.on","true")),
                "Explosions", "settings.explosion.desc", Material.TNT, false);
    }
}
