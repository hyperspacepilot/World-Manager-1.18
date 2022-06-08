package de.hyper.worlds.domain.using;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extension.platform.Actor;
import de.hyper.worlds.domain.WorldManagement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FaweAPI {

    private final List<Player> hasWEA;
    private boolean existsFAWE;

    public FaweAPI() {
        existsFAWE = Bukkit.getPluginManager().isPluginEnabled("FastAsyncWorldEdit");
        if (!existsFAWE) {
            WorldManagement.get().getLogger().warning("Plugin FastAsyncWorldEdit wasn't found, make sure to install FastAsyncWorldEdit to enable FAWE support.");
        }
        hasWEA = new CopyOnWriteArrayList<>();
    }

    public void setWEA(Player player, boolean allowed) {
        if (existsFAWE) {
            if (!hasWEA.contains(player) && allowed) {
                hasWEA.add(player);
                getFaweActor(player).togglePermission("fawe.bypass");
                return;
            } else if (hasWEA.contains(player) && !allowed) {
                hasWEA.remove(player);
                getFaweActor(player).togglePermission("fawe.bypass");
                return;
            }
            hasWEA.remove(player);
        }
    }

    public Actor getFaweActor(Player player) {
        return BukkitAdapter.adapt(player);
    }
}
