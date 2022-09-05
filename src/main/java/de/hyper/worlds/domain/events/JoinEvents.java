package de.hyper.worlds.domain.events;

import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.domain.WorldManagement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ServerUser user = WorldManagement.get().getCache().getServerUser(player.getUniqueId(), player.getName());
        user.update(player);
    }
}