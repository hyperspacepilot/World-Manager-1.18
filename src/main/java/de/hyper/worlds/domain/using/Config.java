package de.hyper.worlds.domain.using;

import de.hyper.worlds.common.obj.ConfigBase;
import de.hyper.worlds.domain.WorldManagement;

import java.io.File;

public class Config extends ConfigBase {

    public Config() {
        super(new File(WorldManagement.getInstance().getDataFolder().getAbsolutePath() + "/config.json"));
        register(
                "language",
                Language.GERMAN,
                "Define the language which should be used. Please notice, that they have to be registered in the messages.yml. Default languages are " + Language.GERMAN + " and " + Language.ENGLISH);
        register(
                "prefix",
                Language.PREFIX,
                "Define the prefix for chat messages.");
        register(
                "history-use",
                true,
                "Define if the history of worlds is enabled (increases storage).");
        register(
                "history-idle-time",
                259200000,
                "Define the time a history record will be stored and deleted after it (given in milliseconds).");
        save();
    }
}