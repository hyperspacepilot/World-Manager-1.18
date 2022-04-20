package de.hyper.worlds.common.obj;

import com.google.gson.Gson;
import de.hyper.worlds.domain.WorldManagement;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.UUID;

@Getter
public class ServerUser {

    private UUID uuid;
    private String name;
    private HashMap<UUID, UUID> worldRoles;

    public ServerUser(UUID uuid) {
        this.uuid = uuid;
        this.name = WorldManagement.get().getName(uuid);
        this.worldRoles = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void update(Player player) {
        if (player.getUniqueId().equals(uuid)) {
            this.name = player.getName();
        }
    }

    /*
     * Used for admissions.
     */
    public WorldRole getWorldRole(ServerWorld serverWorld) {
        UUID roleUniqueID = this.worldRoles.get(serverWorld.getUniqueID());
        for (WorldRole role : serverWorld.getRoles()) {
            if (role.getUniqueID().equals(roleUniqueID)) {
                return role;
            }
        }
        return serverWorld.getDefaultRole();
    }

    /*
     * Used for getting role of a player, who is member of a world, like in the Users/Members Inventory.
     */
    public WorldRole getWorldRoleForList(ServerWorld serverWorld) {
        UUID roleUniqueID = this.worldRoles.get(serverWorld.getUniqueID());
        for (WorldRole role : serverWorld.getRoles()) {
            if (role.getUniqueID().equals(roleUniqueID)) {
                return role;
            }
        }
        return null;
    }

    public void removeRole(ServerWorld serverWorld) {
        this.worldRoles.remove(serverWorld.getUniqueID());
    }

    public void removeRole(UUID worldUUID, UUID roleUUID) {
        if (this.worldRoles.containsKey(worldUUID)) {
            if (this.worldRoles.get(worldUUID).equals(roleUUID)) {
                worldRoles.remove(worldUUID);
            }
        }
    }

    public void put(ServerWorld serverWorld, WorldRole worldRole) {
        this.worldRoles.put(serverWorld.getUniqueID(), worldRole.getUniqueID());
    }
}