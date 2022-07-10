package de.hyper.worlds.common.obj.world.setting.settings;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.setting.SettingState;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import org.bukkit.Material;

public class EntityBlockFormSetting extends WorldSetting {

    public EntityBlockFormSetting() {
        super(SettingType.ENTITY_BLOCK_FORM,
                new SettingState(
                        b("entityformblock.off","false"),
                        b("entityformblock.on","true")),
                "Entity-Form-Block", "settings.entityformblock.desc", Material.SNOW_BLOCK, false);
    }
}
