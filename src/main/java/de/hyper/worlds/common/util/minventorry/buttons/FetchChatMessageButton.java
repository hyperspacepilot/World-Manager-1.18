package de.hyper.worlds.common.util.minventorry.buttons;

import de.hyper.worlds.domain.WorldManagement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public abstract class FetchChatMessageButton extends Button implements Listener {

    private Player player;

    public FetchChatMessageButton(Player player) {
        this.player = player;
    }

    @Override
    public void onClick(InventoryAction event) {
        player.closeInventory();
        WorldManagement.get().getLanguage().send(player, "inventory.general.fetchchatmessage");
        Bukkit.getPluginManager().registerEvents(this, WorldManagement.get());
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (event.getPlayer().getUniqueId().equals(player.getUniqueId())) {
            onReceiveMessage(event.getMessage());
            event.setCancelled(true);
            HandlerList.unregisterAll(this);
        }
    }

    public abstract void onReceiveMessage(String message);
}
