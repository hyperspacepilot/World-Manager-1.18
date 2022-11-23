package de.hyper.worlds.domain.events;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.PlayerUseWorldChatEvent;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.ServerWorldSettingChangedEvent;
import de.hyper.worlds.common.obj.world.setting.StatePart;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import de.hyper.worlds.common.util.Converter;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Performance;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.List;

public class SettingEvents implements Listener {

    Performance performance = WorldManagement.get().getPerformance();

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        performance.checkAndUnloadAllUnloadWorlds();
        Player player = event.getPlayer();
        World world = event.getPlayer().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.BLOCK_SPREAD);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent event) {
        if (event.getEntity() instanceof Animals || event.getEntity() instanceof Mob) {
            World world = event.getEntity().getWorld();
            ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        if (event.getEntity() instanceof Animals || event.getEntity() instanceof Mob) {
            World world = event.getEntity().getWorld();
            ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        if (event.getEntity() instanceof Animals || event.getEntity() instanceof Mob) {
            World world = event.getEntity().getWorld();
            ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.EXPLOSION);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
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
    public void onBlockFade(BlockFadeEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.BLOCK_FADE);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onRedstone(BlockRedstoneEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.REDSTONE);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            if (!value) {
                event.setNewCurrent(0);
            }
        }
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        World world = event.getSourceBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
        if (serverWorld != null) {
            Block block = event.getSourceBlock();
            boolean powerable = isPowerable(block);
            SettingType settingType = powerable ? SettingType.REDSTONE : SettingType.BLOCK_PHYSICS;
            WorldSetting setting = serverWorld.getWorldSetting(settingType);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    public boolean isPowerable(Block block) {
        List<Material> materialList = List.of(Material.REDSTONE, Material.PISTON, Material.STICKY_PISTON,
                Material.OAK_BUTTON, Material.BIRCH_BUTTON, Material.ACACIA_BUTTON,
                Material.CRIMSON_BUTTON, Material.OAK_BUTTON, Material.DARK_OAK_BUTTON,
                Material.JUNGLE_BUTTON, Material.POLISHED_BLACKSTONE_BUTTON, Material.SPRUCE_BUTTON,
                Material.STONE_BUTTON, Material.WARPED_BUTTON, Material.POWERED_RAIL,
                Material.COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK,
                Material.REPEATER, Material.COMPARATOR, Material.REDSTONE_TORCH,
                Material.REDSTONE_LAMP, Material.REDSTONE_WIRE, Material.REDSTONE_WALL_TORCH,
                Material.OBSERVER, Material.HOPPER, Material.DISPENSER,
                Material.DROPPER, Material.LECTERN, Material.TARGET,
                Material.LEVER, Material.LIGHTNING_ROD, Material.DAYLIGHT_DETECTOR,
                Material.TRIPWIRE, Material.TRIPWIRE_HOOK, Material.TRAPPED_CHEST,
                Material.TNT, Material.NOTE_BLOCK, Material.ACACIA_PRESSURE_PLATE,
                Material.BIRCH_PRESSURE_PLATE, Material.CRIMSON_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE,
                Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
                Material.OAK_PRESSURE_PLATE, Material.POLISHED_BLACKSTONE_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE,
                Material.STONE_PRESSURE_PLATE, Material.WARPED_PRESSURE_PLATE, Material.ACACIA_DOOR,
                Material.ACACIA_TRAPDOOR, Material.BIRCH_DOOR, Material.BIRCH_TRAPDOOR,
                Material.CRIMSON_DOOR, Material.CRIMSON_TRAPDOOR, Material.DARK_OAK_DOOR,
                Material.DARK_OAK_TRAPDOOR, Material.IRON_DOOR, Material.IRON_TRAPDOOR,
                Material.JUNGLE_DOOR, Material.JUNGLE_TRAPDOOR, Material.OAK_DOOR,
                Material.OAK_TRAPDOOR, Material.SPRUCE_DOOR, Material.SPRUCE_TRAPDOOR,
                Material.WARPED_DOOR, Material.WARPED_TRAPDOOR, Material.ACACIA_FENCE_GATE,
                Material.BIRCH_FENCE_GATE, Material.CRIMSON_FENCE_GATE, Material.DARK_OAK_FENCE_GATE,
                Material.JUNGLE_FENCE_GATE, Material.OAK_FENCE_GATE, Material.SPRUCE_FENCE_GATE,
                Material.WARPED_FENCE_GATE, Material.REDSTONE_BLOCK);
        return materialList.contains(block.getType());
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        World world = event.getBlock().getWorld();
        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
        if (serverWorld != null) {
            WorldSetting setting = serverWorld.getWorldSetting(SettingType.BLOCK_PHYSICS);
            StatePart part = setting.getState().getActive();
            boolean value = Converter.getBoolean(part.getValue());
            event.setCancelled(!value);
        }
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (WorldManagement.get().getConfiguration().getData("enableable-world-chats").asBoolean()) {
            Player player = event.getPlayer();
            World world = player.getWorld();
            ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
            if (serverWorld != null) {
                WorldSetting setting = serverWorld.getWorldSetting(SettingType.WORLDCHAT);
                StatePart part = setting.getState().getActive();
                boolean value = Converter.getBoolean(part.getValue());
                event.setCancelled(value);
                if (value) {
                    performance.sync(() -> {
                        String message = event.getMessage();
                        PlayerUseWorldChatEvent eventToCall = new PlayerUseWorldChatEvent(player, WorldManagement.get().getCache().getServerUser(player.getUniqueId()), serverWorld, message);
                        Bukkit.getPluginManager().callEvent(eventToCall);
                        if (!eventToCall.isCancelled()) {
                            BaseComponent baseComponent;
                            if (eventToCall.getMessageAsComponent() != null) {
                                baseComponent = eventToCall.getMessageAsComponent();
                            } else {
                                baseComponent = WorldManagement.get().getLoadHelper().getDefaultMessageComponentForWorldChat(eventToCall);
                            }
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                if (players.getWorld().equals(world)) {
                                    players.spigot().sendMessage(baseComponent);
                                }
                            }
                        }
                    });
                }
            }
        }
    }
}