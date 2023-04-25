package de.hyper.worlds.domain.using;

import de.hyper.worlds.common.enums.CategoryType;
import de.hyper.worlds.common.obj.InventoryData;
import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.domain.WorldManagement;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Getter
public class Cache {

    private CopyOnWriteArrayList<ServerUser> serverUsers;
    private ConcurrentHashMap<String, ServerWorld> serverWorlds;
    private ConcurrentHashMap<String, HashMap<String, ArrayList<InventoryData>>> worldPlayerInventories;

    public Cache() {
        load();
    }

    public void save() {
        WorldManagement.get().getSaveSystem().saveWorlds(this.serverWorlds);
        WorldManagement.get().getSaveSystem().saveUsers(this.serverUsers);
        WorldManagement.get().getSaveSystem().saveWorldPlayerInventories(this.worldPlayerInventories);
    }

    public void load() {
        this.serverWorlds = WorldManagement.get().getSaveSystem().getServerWorlds();
        if (this.serverWorlds == null) {
            this.serverWorlds = new ConcurrentHashMap<>();
        }
        this.serverUsers = WorldManagement.get().getSaveSystem().getUsers();
        if (this.serverUsers == null) {
            this.serverUsers = new CopyOnWriteArrayList<>();
        }
        this.worldPlayerInventories = WorldManagement.get().getSaveSystem().getWorldPlayerInventories();
        if (this.worldPlayerInventories == null) {
            this.worldPlayerInventories = new ConcurrentHashMap<>();
        }
        save();
    }

    public void reput(String oldName, String newName, ServerWorld serverWorld) {
        serverWorlds.remove(oldName, serverWorld);
        serverWorlds.put(newName, serverWorld);
    }

    public void remove(ServerWorld serverWorld) {
        serverWorlds.remove(serverWorld.getWorldName(), serverWorld);
    }

    public List<ServerWorld> getAllServerWorlds() {
        return this.serverWorlds.values().stream().collect(Collectors.toList());
    }

    public HashMap<String, ArrayList<InventoryData>> getInventoriesFromWorld(ServerWorld serverWorld) {
        return this.worldPlayerInventories.get(serverWorld.getUniqueID().toString());
    }

    public ArrayList<InventoryData> getInventoriesFromPlayerAndWorld(ServerWorld serverWorld, ServerUser serverUser) {
        return this.worldPlayerInventories.get(serverWorld.getUniqueID().toString()).get(serverUser.getUuid().toString());
    }

    public ArrayList<InventoryData> getInventoriesFromPlayerAndWorld(ServerWorld serverWorld, Player player) {
        return this.worldPlayerInventories.get(serverWorld.getUniqueID().toString()).get(player.getUniqueId().toString());
    }

    public List<ServerWorld> getServerWorldsFromPlayer(Player player) {
        return getAllServerWorlds().stream().filter(serverWorld -> serverWorld.getOwnerUUID().equals(player.getUniqueId())).collect(Collectors.toList());
    }

    public List<ServerWorld> getServerWorldsBySimilarName(String name) {
        List<ServerWorld> list = new CopyOnWriteArrayList<>();
        for (ServerWorld serverWorld : this.serverWorlds.values()) {
            if (serverWorld.getWorldName().toLowerCase().contains(name.toLowerCase())) {
                list.add(serverWorld);
            }
        }
        return list;
    }

    public List<ServerWorld> getServerWorldsByCategory(CategoryType categoryType) {
        List<ServerWorld> list = new CopyOnWriteArrayList<>();
        for (ServerWorld serverWorld : this.serverWorlds.values()) {
            if (serverWorld.getCategoryType().equals(categoryType)) {
                list.add(serverWorld);
            }
        }
        return list;
    }

    public List<ServerUser> getUsersOfWorld(ServerWorld serverWorld) {
        List<ServerUser> list = new ArrayList<>();
        for (ServerUser user : this.serverUsers) {
            if (user.getWorldRoleForList(serverWorld) != null) {
                list.add(user);
            }
        }
        return list;
    }

    public void removeRole(UUID worldUUID, UUID roleUUID) {
        for (ServerUser user : serverUsers) {
            user.removeRole(worldUUID, roleUUID);
        }
    }

    public boolean isLoadedWorld(String worldName) {
        boolean loaded = false;
        for (World w : Bukkit.getWorlds()) {
            if (w.getName().equals(worldName)) {
                loaded = true;
                break;
            }
        }
        return loaded;
    }

    public World getWorld(ServerWorld serverWorld) {
        return Bukkit.getWorld(serverWorld.getWorldName());
    }

    public boolean existsServerWorld(String worldName) {
        return this.serverWorlds.containsKey(worldName);
    }

    public ServerWorld getServerWorld(World world) {
        return this.getServerWorld(world.getName());
    }

    public ServerWorld getServerWorld(String worldName) {
        return this.serverWorlds.get(worldName);
    }

    public void addServerWorld(ServerWorld serverWorld) {
        this.serverWorlds.put(serverWorld.getWorldName(), serverWorld);
    }

    public ServerUser getServerUser(String name) {
        return getServerUser(WorldManagement.get().getUniqueID(name));
    }

    public ServerUser getServerUser(UUID uuid) {
        for (ServerUser serverUser : this.serverUsers) {
            if (serverUser.getUuid().equals(uuid)) {
                return serverUser;
            }
        }
        ServerUser newUser = new ServerUser(uuid);
        this.serverUsers.add(newUser);
        return newUser;
    }

    public ServerUser getServerUserByStringUUID(String uuid) {
        for (ServerUser serverUser : this.serverUsers) {
            if (serverUser.getUuid().toString().equals(uuid)) {
                return serverUser;
            }
        }
        return null;
    }

    public ServerUser getServerUser(UUID uuid, String name) {
        for (ServerUser serverUser : this.serverUsers) {
            if (serverUser.getUuid().equals(uuid)) {
                return serverUser;
            }
        }
        ServerUser newUser = new ServerUser(uuid, name);
        this.serverUsers.add(newUser);
        return newUser;
    }
}