package de.hyper.worlds.domain.events;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.ServerWorld;
import de.hyper.worlds.common.obj.ServerWorldSettingChangedEvent;
import de.hyper.worlds.common.obj.StatePart;
import de.hyper.worlds.common.obj.WorldSetting;
import de.hyper.worlds.common.obj.settings.BlockGrowSetting;
import de.hyper.worlds.common.obj.settings.BlockSpreadSetting;
import de.hyper.worlds.common.util.Converter;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Performance;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class SettingEvents implements Listener {

    Performance performance = WorldManagement.get().getPerformance();

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        performance.checkAndUnloadAllUnloadWorlds();
        Player player = event.getPlayer();
        World world = event.getPlayer().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            if (!player.hasPermission("worldmanager.admin.bypass.settings.gamemode")) {
                WorldSetting setting = serverWorld.getWorldSetting(SettingType.GAMEMODE);
                StatePart part = setting.getState().getActive();
                String value = part.getValue();
                if (!value.equals("keep")) {
                    player.setGameMode(GameMode.valueOf(value.toUpperCase()));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        World world = event.getPlayer().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            if (!player.hasPermission("worldmanager.admin.bypass.settings.gamemode")) {
                WorldSetting setting = serverWorld.getWorldSetting(SettingType.GAMEMODE);
                StatePart part = setting.getState().getActive();
                String value = part.getValue();
                if (!value.equals("keep")) {
                    player.setGameMode(GameMode.valueOf(value.toUpperCase()));
                }
            }
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.BLOCK_BURN);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onBlockFertilize(BlockFertilizeEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.BLOCK_FERTILIZE);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onBlockGrow(BlockGrowEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.BLOCK_GROW);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.BLOCK_FROM_TO);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.BLOCK_FORM);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onEntityBlockForm(EntityBlockFormEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.ENTITY_BLOCK_FORM);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.BLOCK_SPREAD);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent event) {
        if (event.getEntity() instanceof Animals ||event.getEntity() instanceof Mob) {
            World world = event.getEntity().getWorld();
            ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
            if (serverWorld != null) {
                WorldSetting setting = serverWorld.getWorldSetting(SettingType.MOB_SPAWNING);
                StatePart part = setting.getState().getActive();
                boolean value = Converter.getBoolean(part.getValue());
                event.setCancelled(!value);
            }
        }
    }

    @EventHandler
    public void onCreateSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Animals ||event.getEntity() instanceof Mob) {
            World world = event.getEntity().getWorld();
            ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
            if (serverWorld != null) {
                WorldSetting setting = serverWorld.getWorldSetting(SettingType.MOB_SPAWNING);
                StatePart part = setting.getState().getActive();
                boolean value = Converter.getBoolean(part.getValue());
                event.setCancelled(!value);
            }
        }
    }

    @EventHandler
    public void onEntityDropItem(EntityDropItemEvent event) {
        if (event.getEntity() instanceof Animals ||event.getEntity() instanceof Mob) {
            World world = event.getEntity().getWorld();
            ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
            if (serverWorld != null) {
                WorldSetting setting = serverWorld.getWorldSetting(SettingType.MOB_DROPS);
                StatePart part = setting.getState().getActive();
                boolean value = Converter.getBoolean(part.getValue());
                event.setCancelled(!value);
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        World world = event.getEntity().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.HUNGER);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
            event.getEntity().setFoodLevel(20);
        }
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        World world = event.getEntity().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.POTION);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        World world = event.getEntity().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.POTION);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onLingeringPotionSplash(LingeringPotionSplashEvent event) {
        World world = event.getEntity().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.POTION);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        performance.checkAndUnloadAllUnloadWorlds();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        performance.checkAndUnloadAllUnloadWorlds();
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.EXPLOSION);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        World world = event.getEntity().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.EXPLOSION);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onRedstone(BlockRedstoneEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.LEAF_DECAY);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            if (!value) {
                event.setNewCurrent(0);
            }
        }
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.LEAF_DECAY);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onServerWorldChangedSetting(ServerWorldSettingChangedEvent event) {
        World world = event.getWorld();
        ServerWorld serverWorld = event.getServerWorld();
        SettingType settingType = event.getSettingType();
        WorldSetting setting = serverWorld.getWorldSetting(event.getSettingType());
        StatePart part = setting.getState().getActive();
        String value = part.getValue();
        if (settingType == SettingType.TIME) {
            GameRule gameRule = GameRule.DO_DAYLIGHT_CYCLE;
            if (value.equals("-1")) {
                world.setGameRule(gameRule, true);
            } else {
                world.setGameRule(gameRule, false);
                world.setTime(Converter.getPositiveLong(value));
            }
        }
        if (settingType == SettingType.WEATHER) {
            GameRule gameRule = GameRule.DO_WEATHER_CYCLE;
            if (value.equals("running")) {
                world.setGameRule(gameRule, true);
                return;
            }
            world.setGameRule(gameRule, false);
            if (value.equals("sun")) {
                world.setStorm(false);
                world.setThundering(false);
            } else if (value.equals("rain")) {
                world.setStorm(true);
                world.setThundering(false);
            } else if (value.equals("storm")) {
                world.setStorm(true);
                world.setThundering(true);
            }
        }
        if (settingType == SettingType.RANDOMTICKSPEED) {
            GameRule gameRule = GameRule.RANDOM_TICK_SPEED;
            world.setGameRule(gameRule, Converter.getInteger(value));
        }
        if (settingType == SettingType.SIZE) {
            WorldBorder border = world.getWorldBorder();
            border.setCenter(serverWorld.getSpawnLocation());
            border.setSize(Converter.getDouble(value));
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        World world = event.getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.WEATHER);
            StatePart part = setting.getState().getActive();
            String value = part.getValue();
            if (!value.equals("running")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        World world = event.getSourceBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.BLOCK_PHYSICS);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.BLOCK_PHYSICS);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }
}