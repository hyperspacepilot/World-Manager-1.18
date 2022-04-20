package de.hyper.worlds.domain.events;

import de.hyper.worlds.common.obj.ServerWorld;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.CacheSystem;
import de.hyper.worlds.domain.using.FaweAPI;
import de.hyper.worlds.domain.using.Performance;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class RoleEvents implements Listener {

    Performance performance = WorldManagement.getInstance().getPerformance();
    CacheSystem cache = WorldManagement.getInstance().getCacheSystem();
    FaweAPI fawe = WorldManagement.get().getFawe();

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ServerWorld serverWorld = cache.getServerWorld(event.getPlayer().getWorld().getName());
        if (serverWorld != null) {
            if (!serverWorld.isAllowed(event.getPlayer(), "item.drop")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            ServerWorld serverWorld = cache.getServerWorld(((Player) event.getEntity()).getWorld().getName());
            if (serverWorld != null) {
                if (!serverWorld.isAllowed(((Player) event.getEntity()), "item.pickup")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        ServerWorld serverWorld = cache.getServerWorld(event.getPlayer().getWorld().getName());
        if (serverWorld != null) {
            if (!serverWorld.isAllowed(event.getPlayer(), "blocks.break")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ServerWorld serverWorld = cache.getServerWorld(event.getPlayer().getWorld().getName());
        if (serverWorld != null) {
            if (!serverWorld.isAllowed(event.getPlayer(), "blocks.place")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = event.getPlayer().getWorld();
        ServerWorld serverWorld = cache.getServerWorld(world.getName());
        if (serverWorld.isAllowed(player, "worldedit")) {
            fawe.setWEA(player, true);
        } else {
            fawe.setWEA(player, false);
        }
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = event.getPlayer().getWorld();
        ServerWorld serverWorld = cache.getServerWorld(world.getName());
        if (!serverWorld.isAllowed(player, "enter")) {
            player.teleport(event.getFrom().getSpawnLocation());
            return;
        }
        if (serverWorld.isAllowed(player, "worldedit")) {
            fawe.setWEA(player, true);
        } else {
            fawe.setWEA(player, false);
        }
    }
}