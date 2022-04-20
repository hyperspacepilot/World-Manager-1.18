package de.hyper.worlds.domain.using;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extension.platform.Actor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FaweAPI {

    private List<Player> hasWEA;

    public FaweAPI() {
        hasWEA = new CopyOnWriteArrayList<>();
    }

    public void setWEA(Player player, boolean allowed) {
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

    public Actor getFaweActor(Player player) {
        return BukkitAdapter.adapt(player);
    }
}
