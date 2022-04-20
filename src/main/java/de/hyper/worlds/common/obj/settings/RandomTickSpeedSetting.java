package de.hyper.worlds.common.obj.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.SettingState;
import de.hyper.worlds.common.obj.WorldSetting;
import de.hyper.worlds.common.util.items.ItemBuilder;
import org.bukkit.Material;

public class RandomTickSpeedSetting extends WorldSetting {

    public RandomTickSpeedSetting() {
        super(SettingType.RANDOMTICKSPEED,
                new SettingState(
                        b("randomtickspeed.stopped","0"),
                        b("randomtickspeed.slow","1"),
                        b("randomtickspeed.normal","3"),
                        b("randomtickspeed.fast","5"),
                        b("randomtickspeed.ultra","7"),
                        b("randomtickspeed.highspeed","10"),
                        b("randomtickspeed.overkill","25")),
                "Random-Tick-Speed", "settings.randomtickspeed.desc", Material.WHEAT_SEEDS, true);
    }
}
