package de.hyper.worlds.domain;

import de.hyper.worlds.domain.commands.WorldCommand;
import de.hyper.worlds.domain.events.JoinEvents;
import de.hyper.worlds.domain.events.RoleEvents;
import de.hyper.worlds.domain.events.SettingEvents;
import de.hyper.worlds.domain.using.*;
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
    private CacheSystem cacheSystem;
    private LoadHelper loadHelper;
    private Inventories inventories;
    private Language language;
    private FaweAPI fawe;
    private Config configuration;
    private PluginManager pluginManager = Bukkit.getPluginManager();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.performance = new Performance();
        this.saveSystem = new SaveSystem();
        this.configuration = new Config();
        this.cacheSystem = new CacheSystem();
        this.loadHelper = new LoadHelper();
        this.fawe = new FaweAPI();
        this.language = new Language(configuration.getData("language").getDataValueAsString());
        this.inventories = new Inventories(this);
        this.getCommand("world").setExecutor(new WorldCommand());
        pluginManager.registerEvents(new RoleEvents(), this);
        pluginManager.registerEvents(new JoinEvents(), this);
        pluginManager.registerEvents(new SettingEvents(), this);
    }

    @Override
    public void onDisable() {
        this.cacheSystem.save();
        this.language.unload();
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
            if (offPlayer.getUniqueId().toString().equals(uniqueID)) {
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