package de.hyper.worlds.domain.using;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.ServerWorld;
import de.hyper.worlds.domain.WorldManagement;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SaveSystem {

    private final Gson gson;
    private final File saveWorldsFile;
    private final File saveUsersFile;
    private final File saveMessagesFile;

    @SneakyThrows
    public SaveSystem() {
        WorldManagement.get().getDataFolder().mkdirs();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        WorldManagement.getInstance().getDataFolder().mkdirs();
        this.saveWorldsFile = new File(WorldManagement.getInstance().getDataFolder().getAbsolutePath() + "/worlds.json");
        this.saveUsersFile = new File(WorldManagement.getInstance().getDataFolder().getAbsolutePath() + "/users.json");
        this.saveMessagesFile = new File(WorldManagement.getInstance().getDataFolder().getAbsolutePath() + "/messages.json");
        try {
            if (!this.saveWorldsFile.exists()) {
                this.saveWorldsFile.createNewFile();
            }
            if (!this.saveUsersFile.exists()) {
                this.saveUsersFile.createNewFile();
            }
            if (!this.saveMessagesFile.exists()) {
                this.saveMessagesFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void saveUsers(CopyOnWriteArrayList<ServerUser> list) {
        try (FileWriter fileWriter = new FileWriter(this.saveUsersFile.getAbsolutePath())) {
            this.gson.toJson(list, new TypeToken<CopyOnWriteArrayList<ServerUser>>() {
            }.getType(), fileWriter);
            fileWriter.flush();
        }
    }

    @SneakyThrows
    public CopyOnWriteArrayList<ServerUser> getUsers() {
        try (FileReader reader = new FileReader(this.saveUsersFile)) {
            return this.gson.fromJson(reader, new TypeToken<CopyOnWriteArrayList<ServerUser>>() {
            }.getType());
        }
    }

    @SneakyThrows
    public void saveWorlds(ConcurrentHashMap<String, ServerWorld> map) {
        try (
                FileWriter fileWriter = new FileWriter(this.saveWorldsFile.getAbsolutePath())) {
            this.gson.toJson(map, new TypeToken<ConcurrentHashMap<String, ServerWorld>>() {
            }.getType(), fileWriter);
            fileWriter.flush();
        }
    }

    @SneakyThrows
    public ConcurrentHashMap<String, ServerWorld> getServerWorlds() {
        try (FileReader reader = new FileReader(this.saveWorldsFile)) {
            return this.gson.fromJson(reader, new TypeToken<ConcurrentHashMap<String, ServerWorld>>() {
            }.getType());
        }
    }

    @SneakyThrows
    public void saveMessages(HashMap<String, HashMap<String, String>> map) {
        try (FileWriter fileWriter = new FileWriter(this.saveMessagesFile.getAbsolutePath())) {
            this.gson.toJson(map, new TypeToken<HashMap<String, HashMap<String, String>>>() {
            }.getType(), fileWriter);
            fileWriter.flush();
        }
    }

    @SneakyThrows
    public HashMap<String, HashMap<String, String>> getMessages() {
        try (FileReader reader = new FileReader(this.saveMessagesFile)) {
            return this.gson.fromJson(reader, new TypeToken<HashMap<String, HashMap<String, String>>>() {
            }.getType());
        }
    }
}