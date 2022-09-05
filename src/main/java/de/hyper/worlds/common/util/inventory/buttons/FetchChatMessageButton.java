package de.hyper.worlds.common.util.inventory.buttons;

import de.hyper.worlds.domain.WorldManagement;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@AllArgsConstructor
public abstract class FetchChatMessageButton extends Button implements Listener {

    protected Player player;


    @Override
    public void onClick(InventoryAction inventoryAction) {
        onStartListening(inventoryAction);
        WorldManagement.get().getLanguage().send(player, "inventory.general.fetchchatmessage");
        player.closeInventory();
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

    public abstract void onStartListening(InventoryAction inventoryAction);

    public abstract void onReceiveMessage(String message);
}