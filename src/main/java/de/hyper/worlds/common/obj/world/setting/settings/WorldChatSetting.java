package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class WorldChatSetting extends WorldSetting {

    public WorldChatSetting() {
        super(SettingType.WORLDCHAT,
                new SettingState(
                        b("worldchat.off", "false"),
                        b("worldchat.on", "true")),
                "World-Chat", "settings.worldchat.desc", Material.FILLED_MAP, false);
    }
}