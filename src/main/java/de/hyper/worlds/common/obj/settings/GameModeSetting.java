package de.hyper.worlds.common.obj.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.SettingState;
import de.hyper.worlds.common.obj.WorldSetting;
import org.bukkit.Material;

public class GameModeSetting extends WorldSetting {

    public GameModeSetting() {
        super(SettingType.GAMEMODE,
                new SettingState(
                        b("gamemode.keep","keep"),
                        b("gamemode.survival","survival"),
                        b("gamemode.creative","creative"),
                        b("gamemode.spectator","spectator"),
                        b("gamemode.adventure","adventure")),
                "GameMode", "settings.gamemode.desc", Material.BEDROCK, false);
    }
}
