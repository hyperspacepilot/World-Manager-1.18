package de.hyper.worlds.domain.using;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.ServerWorld;
import de.hyper.worlds.common.util.Converter;
import de.hyper.worlds.domain.WorldManagement;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Performance {

    private final ThreadPoolExecutor threads;

    public Performance() {
        this.threads = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
    }

    public void async(Runnable run) {
        threads.execute(run);
    }

    public void sync(Runnable run) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(WorldManagement.get(), run);
    }

    public void checkAndUnloadAllUnloadWorlds() {
        async(() -> {
            for (World world : Bukkit.getWorlds()) {
                if (world.getPlayers().isEmpty()) {
                    if (WorldManagement.get().getCacheSystem().existsServerWorld(world.getName())) {
                        ServerWorld serverWorld = WorldManagement.get().getCacheSystem().getServerWorld(world.getName());
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