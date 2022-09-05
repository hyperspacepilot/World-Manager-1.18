package de.hyper.worlds.domain.using;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.Converter;
import de.hyper.worlds.domain.WorldManagement;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Performance {

    private final Executor threads;

    public Performance() {
        this.threads = Executors.newCachedThreadPool();
    }

    public void async(Runnable run) {
        threads.execute(run);
    }

    public void sync(Runnable run) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(WorldManagement.get(), run);
    }


    public void unloadWorld(ServerWorld serverWorld) {
        unloadWorld(serverWorld.getBukkitWorld());
    }

    public void unloadWorld(World world) {
        if (world.getPlayers().isEmpty()) {
            sync(() -> {
                Bukkit.unloadWorld(world, true);
            });
        }
    }

    public void checkAndUnloadAllUnloadWorlds() {
        async(() -> {
            for (World world : Bukkit.getWorlds()) {
                if (world.getPlayers().isEmpty()) {
                    if (WorldManagement.get().getCache().existsServerWorld(world.getName())) {
                        ServerWorld serverWorld = WorldManagement.get().getCache().getServerWorld(world.getName());
                        if (Converter.getBoolean(serverWorld.getWorldSetting(SettingType.UNLOADING).getState().getActive().getValue())) {
                            if (!serverWorld.isIgnoreGeneration()) {
                                sync(() -> {
                                    Bukkit.unloadWorld(world, true);
                                });
                            }
                        }
                    }
                }
            }
        });
    }
}