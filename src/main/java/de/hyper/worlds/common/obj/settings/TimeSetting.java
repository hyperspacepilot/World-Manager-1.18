package de.hyper.worlds.common.obj.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.SettingState;
import de.hyper.worlds.common.obj.WorldSetting;
import de.hyper.worlds.common.util.items.ItemBuilder;
import org.bukkit.Material;

public class TimeSetting extends WorldSetting {

    public TimeSetting() {
        super(SettingType.TIME,
                new SettingState(
                        b("time.day","1000"),
                        b("time.midday","5000"),
                        b("time.night","13000"),
                        b("time.midnight","15000"),
                        b("time.running","-1")),
                "Time", "settings.time.desc", Material.CLOCK, false);
    }
}