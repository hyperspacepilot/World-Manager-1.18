package de.hyper.worlds.common.obj.world;

import de.hyper.worlds.common.obj.ServerUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public class PlayerUseWorldChatEvent extends Event implements Cancellable {

    @Getter
    private static final HandlerList HandlerList = new HandlerList();

    private final Player senderPlayer;
    private final ServerUser senderServerUser;
    private final ServerWorld serverWorld;
    private final String message;
    @Setter
    private BaseComponent worldInfoComponent = null;
    @Setter
    private BaseComponent playerDisplayNameComponent = null;
    @Setter
    private BaseComponent playerMessageComponent = null;
    @Setter
    private BaseComponent partsSplitComponent = null;
    @Setter
    private boolean cancelled = false;

    public BaseComponent getMessageAsComponent() {
        if (worldInfoComponent != null && playerMessageComponent != null && playerDisplayNameComponent != null && partsSplitComponent != null) {
            BaseComponent baseComponent = new TextComponent(
                    worldInfoComponent,
                    partsSplitComponent,
                    playerDisplayNameComponent,
                    partsSplitComponent,
                    playerMessageComponent);
            return baseComponent;
        }
        return null;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }
}