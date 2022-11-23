package de.hyper.worlds.domain;

import de.hyper.worlds.common.enums.SettingType;
import de.hyper.worlds.common.obj.Dependency;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.Converter;
import de.hyper.worlds.common.util.inventory.InventoryManager;
import de.hyper.worlds.domain.commands.WorldCommand;
import de.hyper.worlds.domain.events.JoinEvents;
import de.hyper.worlds.domain.events.RoleEvents;
import de.hyper.worlds.domain.events.SettingEvents;
import de.hyper.worlds.domain.using.*;
import de.hyper.worlds.domain.using.apis.CoreProtectAPI;
import de.hyper.worlds.domain.using.apis.FaweAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

@Getter
public class WorldManagement extends JavaPlugin {

    private int maxRoles = 18;
    private Performance performance;
    private SaveSystem saveSystem;
    private Cache cache;
    private LoadHelper loadHelper;
    private Language language;
    private Config configuration;
    private PluginManager pluginManager = Bukkit.getPluginManager();
    private InventoryManager inventoryManager;
    private DependencyManager dependencyManager;
    private FaweAPI fawe;
    private CoreProtectAPI coreProtectAPI;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.performance = new Performance();
        this.saveSystem = new SaveSystem();
        this.configuration = new Config();
        this.cache = new Cache();
        this.loadHelper = new LoadHelper();
        this.language = new Language(configuration.getData("language").asString());
        this.inventoryManager = new InventoryManager(this, "WIS");
        this.dependencyManager = new DependencyManager(
                new Dependency("com.fastasyncworldedit.bukkit.FaweBukkit"),
                new Dependency("net.coreprotect.CoreProtect"));
        this.fawe = new FaweAPI();
        this.coreProtectAPI = new CoreProtectAPI();
        this.getCommand("world").setExecutor(new WorldCommand());
        pluginManager.registerEvents(new RoleEvents(), this);
        pluginManager.registerEvents(new JoinEvents(), this);
        pluginManager.registerEvents(new SettingEvents(), this);
        for (ServerWorld serverWorld : this.cache.getAllServerWorlds()) {
            if ((Converter.getBoolean(serverWorld.getWorldSetting(SettingType.UNLOADING).getState().getActive().getValue()))) {
                serverWorld.load();
            }
            serverWorld.importMissingSettings();
        }
    }

    @Override
    public void onDisable() {
        this.cache.save();
        this.language.save();
        this.configuration.save();
    }

    public UUID getUniqueID(String name) {
        for (OfflinePlayer offPlayer : Bukkit.getOfflinePlayers()) {
            if (offPlayer.getName().equals(name)) {
                return offPlayer.getUniqueId();
            }
        }
        return null;
    }

    public String getName(UUID uniqueID) {
        for (OfflinePlayer offPlayer : Bukkit.getOfflinePlayers()) {
            if (offPlayer.getUniqueId().toString().equals(uniqueID.toString())) {
                return offPlayer.getName();
            }
        }
        return "Unknown";
    }

    private static WorldManagement instance;

    public static WorldManagement get() {
        return instance;
    }

    public static WorldManagement getInstance() {
        return instance;
    }
}