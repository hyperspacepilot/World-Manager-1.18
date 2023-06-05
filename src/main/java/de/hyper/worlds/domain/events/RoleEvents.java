package de.hyper.worlds.domain.events;

import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Cache;
import de.hyper.worlds.domain.using.apis.FaweAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.*;

public class RoleEvents implements Listener {

    Cache cache = WorldManagement.getInstance().getCache();
    FaweAPI fawe = WorldManagement.get().getFawe();

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        ServerWorld serverWorld = cache.getServerWorld(event.getPlayer().getWorld().getName());
        if (serverWorld != null) {
            if (!serverWorld.isAllowed(event.getPlayer(), "fish")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerHarvestBlock(PlayerHarvestBlockEvent event) {
        ServerWorld serverWorld = cache.getServerWorld(event.getPlayer().getWorld().getName());
        if (serverWorld != null) {
            if (!serverWorld.isAllowed(event.getPlayer(), "blocks.harvest")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        ServerWorld serverWorld = cache.getServerWorld(event.getPlayer().getWorld().getName());
        if (serverWorld != null) {
            boolean isAllowed = serverWorld.isAllowed(event.getPlayer(), "interact.at.entity");
            if (!isAllowed) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerClickEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ServerWorld serverWorld = cache.getServerWorld(event.getDamager().getWorld().getName());
            if (serverWorld != null) {
                boolean isAllowed = serverWorld.isAllowed(player, "interact.at.entity");
                if (!isAllowed) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ServerWorld serverWorld = cache.getServerWorld(event.getPlayer().getWorld().getName());
        if (serverWorld != null) {
            if (!serverWorld.isAllowed(event.getPlayer(), "interact.general")) {
                event.setCancelled(true);
            }
        }
    }

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
        if (serverWorld != null) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(WorldManagement.getInstance(), () -> {
                if (serverWorld.isAllowed(player, "worldedit")) {
                    fawe.setWEA(player, true);
                } else {
                    fawe.setWEA(player, false);
                }
            }, 60L);
        }
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = event.getPlayer().getWorld();
        ServerWorld serverWorld = cache.getServerWorld(world.getName());
        if (serverWorld != null) {
            if (!serverWorld.isAllowed(player, "enter")) {
                player.teleport(event.getFrom().getSpawnLocation());
                return;
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(WorldManagement.getInstance(), () -> {

                if (serverWorld.isAllowed(player, "worldedit")) {
                    fawe.setWEA(player, true);
                } else {
                    fawe.setWEA(player, false);
                }
            }, 60L);

        }
    }
}