package de.hyper.worlds.common.obj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ConfigBase {

    private final File file;
    private final Gson gson;
    private ArrayList<ConfigData> datas;

    @SneakyThrows
    public ConfigBase(File file) {
        this.file = file;
        if (!file.exists()) {
            file.createNewFile();
        }
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        load();
        if (this.datas == null) {
            this.datas = new ArrayList<>();
        }
    }

    public ConfigData register(String name, Object defaultValue, String description) {
        boolean isRegistered = false;
        for (ConfigData data : this.datas) {
            if (data.getName().equalsIgnoreCase(name)) {
                isRegistered = true;
            }
        }
        ConfigData configData = null;
        if (!isRegistered) {
            configData = new ConfigData(name, description, defaultValue);
            this.datas.add(configData);
        }
        if (configData == null) {
            configData = getData(name);
        }
        return configData;
    }

    public ConfigData getData(String name) {
        ConfigData configData = null;
        for (ConfigData data : this.datas) {
            if (data.getName().equalsIgnoreCase(name)) {
                configData = data;
            }
        }
        if (configData == null) {
            configData = register(name, "Unknown", "Unknown");
        }
        return configData;
    }

    @SneakyThrows
    public void load() {
        try (FileReader reader = new FileReader(this.file)) {
            this.datas = this.gson.fromJson(reader, typeToken());
        }
    }

    @SneakyThrows
    public void save() {
        try (FileWriter writer = new FileWriter(this.file.getAbsolutePath())) {
            gson.toJson(datas, typeToken(), writer);
            writer.flush();
        }
    }

    public Type typeToken() {
        return new com.google.common.reflect.TypeToken<ArrayList<ConfigData>>() {
        }.getType();
    }
}