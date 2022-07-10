package de.hyper.worlds.common.obj.world;

import de.hyper.worlds.common.enums.SettingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor @Getter
public class ServerWorldSettingChangedEvent extends Event {

    @Getter private static final HandlerList HandlerList = new HandlerList();

    private ServerWorld serverWorld;
    private Player player;
    private World world;
    private SettingType settingType;

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }
}
