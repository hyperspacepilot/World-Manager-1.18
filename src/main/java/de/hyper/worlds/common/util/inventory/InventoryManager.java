package de.hyper.worlds.common.util.inventory;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@Getter
public class InventoryManager implements Listener {

    @Getter
    private static InventoryManager instance;

    private Plugin plugin;
    private String inventoryIdentifier;
    private int id;
    private Map<String, Inventory> inventories;

    public InventoryManager(Plugin plugin, String inventoryIdentifier) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
        if (!inventoryIdentifier.contains("§")) {
            String s = "";
            for (String a : inventoryIdentifier.split("")) {
                s += "§" + a;
            }
            inventoryIdentifier = s;
        }
        this.inventoryIdentifier = inventoryIdentifier;
        this.id = 0;
        this.inventories = new HashMap<>();

        instance = this;
    }

    public String getNewID() {
        String stringID = "";
        for (char c : (id + "").toCharArray()) {
            stringID += "§" + c;
        }
        while (stringID.length() < 8) {
            stringID = "§0" + stringID;
        }
        id++;
        if (id > 9999) {
            id = 0;
        }
        return stringID;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        org.bukkit.inventory.@Nullable Inventory bukkitInventory = event.getClickedInventory();
        InventoryView inventoryView = event.getView();
        if (bukkitInventory != null && inventoryView != null) {
            if (inventoryView.getTitle() != null && inventoryView.getTitle().startsWith(inventoryIdentifier)) {
                event.setCancelled(true);
                String stringID = inventoryView.getTitle().substring(6, 14);
                if (inventories.containsKey(stringID) && inventories.get(stringID).getButtons().containsKey(event.getSlot())) {
                    inventories.get(stringID).getButtons().get(event.getSlot()).onClick(event.getAction());
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        org.bukkit.inventory.@Nullable Inventory bukkitInventory = event.getInventory();
        InventoryView inventoryView = event.getView();
        if (bukkitInventory != null && inventoryView != null) {
            if (inventoryView.getTitle() != null && inventoryView.getTitle().startsWith(inventoryIdentifier)) {
                String stringID = inventoryView.getTitle().substring(6, 14);
                if (inventories.containsKey(stringID)) {
                    Inventory inventory = inventories.get(stringID);
                    if (!inventory.isCloseable()) {
                        inventory.open((Player) event.getPlayer());
                    } else {
                        if (inventory.isInstantDelete()) {
                            inventory.onClose();
                            inventories.remove(stringID);
                        }
                    }
                }
            }
        }
    }
}