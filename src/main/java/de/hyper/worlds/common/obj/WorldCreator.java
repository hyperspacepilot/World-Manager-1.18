package de.hyper.worlds.common.obj;

import de.hyper.worlds.common.enums.GeneratorType;
import lombok.Getter;
import org.bukkit.World;

@Getter
public class WorldCreator {

    protected GeneratorType generatorType;
    protected String worldName;
    protected long seed;
    protected org.bukkit.WorldCreator worldCreator;

    public WorldCreator(GeneratorType generatorType, String worldName, long seed) {
        this.generatorType = generatorType;
        this.worldName = worldName;
        this.seed = seed;
        setUpWorldCreator();
    }

    public void setUpWorldCreator() {
        this.worldCreator = new org.bukkit.WorldCreator(worldName);
        if (seed != -1) {
            worldCreator.seed(seed);
        }
        if (generatorType.getEnvironment() != null) {
            worldCreator.environment(generatorType.getEnvironment());
        }
        if (generatorType.getWorldType() != null) {
            worldCreator.type(generatorType.getWorldType());
        }
        if (generatorType.getChunkGenerator() != null) {
            worldCreator.generator(generatorType.getChunkGenerator());
        }
        worldCreator.generateStructures(generatorType.isGenerateStructures());
    }

    public World create() {
        return worldCreator.createWorld();
    }
}
