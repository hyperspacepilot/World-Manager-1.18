package de.hyper.worlds.common.obj.world;

import de.hyper.worlds.common.enums.CategoryType;
import de.hyper.worlds.common.enums.Difficulty;
import de.hyper.worlds.common.enums.GeneratorType;
import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.Duple;
import de.hyper.worlds.common.obj.InventoryData;
import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.WorldCreator;
import de.hyper.worlds.common.obj.world.role.WorldRole;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import de.hyper.worlds.domain.WorldManagement;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ServerWorld {

    private final UUID uniqueID;
    private String worldName;
    @Setter
    private UUID ownerUUID;
    @Setter
    private sLocation spawnLocation;
    @Setter
    private long seed;
    @Setter
    private Difficulty difficulty;
    private long realSeed;

    @Setter
    private GeneratorType generatorType;
    @Setter
    private CategoryType categoryType;
    private final ArrayList<WorldRole> roles;
    private WorldRole defaultRole;
    private final ArrayList<WorldSetting> settings;
    @Setter
    private boolean ignoreGeneration;

    public ServerWorld(UUID uniqueID, String worldName, UUID ownerUUID, boolean ignoreGeneration, long seed,
                       GeneratorType generatorType, CategoryType categoryType) {
        this.uniqueID = uniqueID;
        this.worldName = worldName;
        this.ownerUUID = ownerUUID;
        this.ignoreGeneration = ignoreGeneration;
        this.spawnLocation = new sLocation(worldName, 0.0, 60.5, 0.0, 0.0f, 0.0f);
        this.seed = seed;
        this.difficulty = Difficulty.PEACEFUL;
        this.realSeed = 0;
        this.generatorType = generatorType;
        this.categoryType = categoryType;
        this.roles = new ArrayList<>();
        this.settings = WorldManagement.get().getLoadHelper().getDefaultWorldSettings();
    }

    public Duple<World, Long> load() {
        long started = System.currentTimeMillis();
        World world = null;
        if (!this.isIgnoreGeneration()) {
            WorldCreator worldCreator = new WorldCreator(this.generatorType, this.worldName, this.seed);
            world = worldCreator.create();
        } else {
            for (World w : Bukkit.getWorlds()) {
                if (w.getName().equals(this.worldName)) {
                    world = w;
                }
            }
        }
        if (this.roles.isEmpty() && this.defaultRole == null) {
            resetRoles();
        }
        if (world != null) {
            this.realSeed = world.getSeed();
            world.setDifficulty(this.difficulty.getDif());
            this.spawnLocation = new sLocation(world.getSpawnLocation());
        }
        for (WorldSetting setting : WorldManagement.get().getLoadHelper().getDefaultWorldSettings()) {
            if (!hasSettingType(setting)) {
                this.settings.add(setting);
            }
        }
        long took = (System.currentTimeMillis() - started);
        return new Duple<>(world, took);
    }

    public boolean hasSettingType(WorldSetting worldSettingToProve) {
        return (this.getWorldSetting(worldSettingToProve.getType()) != null);
    }

    public boolean rename(String newName) {
        if (this.getBukkitWorld().getPlayers().size() > 0) {
            return false;
        }
        String oldName = this.worldName;
        WorldManagement.get().getPerformance().unloadWorld(this);
        File oldFolder = getWorldFolder();
        String parent = oldFolder.getParent();
        if (!oldFolder.exists()) {
            return false;
        }
        File dir = new File(oldFolder.toString());
        if (oldFolder.isDirectory()) {
            String newDir = Paths.get(parent, newName).toString();
            dir.renameTo(new File(newDir));
        }
        this.worldName = newName;
        WorldManagement.get().getCache().reput(oldName, newName, this);
        return true;
    }

    private File getWorldFolder() {
        return getBukkitWorld().getWorldFolder();
    }

    public boolean isAllowedToSee(Player player) {
        return player.hasPermission("worldmanager.category.see." + this.categoryType.getPermission());
    }

    public WorldSetting getWorldSetting(SettingType settingType) {
        for (WorldSetting worldSetting : this.settings) {
            if (worldSetting.getType().equals(settingType)) {
                return worldSetting;
            }
        }
        return null;
    }

    public World getBukkitWorld() {
        return Bukkit.getWorld(this.worldName);
    }

    public List<ServerUser> getMembers() {
        return WorldManagement.get().getCache().getUsersOfWorld(this);
    }

    public boolean isAllowed(Player player, String admissionKey) {
        if (player.hasPermission("worldmanager.admin.bypass.flags." + admissionKey)) {
            return true;
        } else if (isOwner(player)) {
            return true;
        } else return getRoleOfHolder(player).isAllowed(admissionKey);
    }

    public void resetRoles() {
        this.roles.clear();
        this.defaultRole = WorldManagement.get().getLoadHelper().getDefaultRole();
        for (WorldRole defaultRoleClone : WorldManagement.get().getLoadHelper().cloneOfDefaultRoles()) {
            defaultRoleClone.setUniqueID(WorldManagement.get().getLoadHelper().createSaveUUID());
            this.roles.add(defaultRoleClone);
        }
    }

    public void deleteRole(WorldRole role) {
        if (this.roles.contains(role)) {
            WorldManagement.get().getCache().removeRole(this.uniqueID, role.getUniqueID());
            this.roles.remove(role);
        }
    }

    public void addRole(WorldRole role) {
        if (!this.roles.contains(role)) {
            this.roles.add(role);
        }
    }

    public boolean existsRoleWithName(String name) {
        boolean exists = false;
        for (WorldRole role : roles) {
            if (role.getName().equalsIgnoreCase(name)) {
                exists = true;
            }
        }
        return exists;
    }

    public WorldRole getRole(String name) {
        for (WorldRole role : roles) {
            if (role.getName().equalsIgnoreCase(name)) {
                return role;
            }
        }
        return null;
    }

    public long getRealSeedOfBukkitWorld() {
        return this.realSeed;
    }

    public WorldRole getRoleOfHolder(Player uuid) {
        return getRoleOfHolder(uuid.getUniqueId());
    }

    public WorldRole getRoleOfHolder(UUID uuid) {
        ServerUser serverUser = WorldManagement.get().getCache().getServerUser(uuid);
        return serverUser.getWorldRole(this);
    }

    public Location getSpawnLocation() {
        return this.spawnLocation.getLocation();
    }

    public boolean isOwner(Player player) {
        return isOwner(player.getUniqueId());
    }

    public boolean isOwner(UUID uuid) {
        return this.ownerUUID.equals(uuid);
    }

    public ArrayList<InventoryData> getInventoriesFromServerUser(ServerUser serverUser) {
        return WorldManagement.get().getCache().getInventoriesFromPlayerAndWorld(this, serverUser);
    }

    public ArrayList<InventoryData> getInventoriesFromPlayer(Player player) {
        return WorldManagement.get().getCache().getInventoriesFromPlayerAndWorld(this, player);
    }
}