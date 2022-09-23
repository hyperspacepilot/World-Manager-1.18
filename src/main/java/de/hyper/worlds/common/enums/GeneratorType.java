package de.hyper.worlds.common.enums;

import com.google.gson.annotations.SerializedName;
import de.hyper.worlds.common.obj.world.generators.FlatChunkGenerator;
import de.hyper.worlds.common.obj.world.generators.OceanChunkGenerator;
import de.hyper.worlds.common.obj.world.generators.VoidChunkGenerator;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;

@Getter
public enum GeneratorType {
    @SerializedName("gen-normal") NORMAL("Normal", FilterGeneratorType.NORMAL, null, WorldType.NORMAL, World.Environment.NORMAL, true),
    @SerializedName("gen-large") LARGE_BIOMES("Large Biomes", FilterGeneratorType.LARGE_BIOMES, null, WorldType.LARGE_BIOMES, World.Environment.NORMAL, true),
    @SerializedName("gen-flat") FLAT("Flat", FilterGeneratorType.FLAT, new FlatChunkGenerator(), null, World.Environment.NORMAL, false),
    @SerializedName("gen-void") VOID("Void", FilterGeneratorType.VOID, new VoidChunkGenerator(), null, World.Environment.NORMAL, false),
    @SerializedName("gen-end") END("End", FilterGeneratorType.END, null, null, World.Environment.THE_END, true),
    @SerializedName("gen-nether") NETHER("Nether", FilterGeneratorType.NETHER, null, null, World.Environment.NETHER, true),
    @SerializedName("gen-ocean") OCEAN("Ocean", FilterGeneratorType.OCEAN, new OceanChunkGenerator(), null, World.Environment.NORMAL, false);

    private final String name;
    private final FilterGeneratorType filter;
    private ChunkGenerator chunkGenerator;
    private WorldType worldType;
    private World.Environment environment;
    private boolean generateStructures;

    GeneratorType(String name, FilterGeneratorType filter, ChunkGenerator chunkGenerator, WorldType worldType, World.Environment environment, boolean generateStructures) {
        this.name = name;
        this.filter = filter;
        this.chunkGenerator = chunkGenerator;
        this.worldType = worldType;
        this.environment = environment;
        this.generateStructures = generateStructures;
    }

    public World create(String worldName, long seed) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.environment(World.Environment.NORMAL);
        switch (this) {
            case NORMAL:
                worldCreator.type(WorldType.NORMAL);
                break;
            case LARGE_BIOMES:
                worldCreator.type(WorldType.LARGE_BIOMES);
                break;
            case FLAT:
                worldCreator.generator(new FlatChunkGenerator());
                worldCreator.generateStructures(false);
                break;
            case VOID:
                worldCreator.generator(new VoidChunkGenerator());
                break;
            case END:
                worldCreator.environment(World.Environment.THE_END);
                break;
            case NETHER:
                worldCreator.environment(World.Environment.NETHER);
                break;
            case OCEAN:
                worldCreator.generator(new OceanChunkGenerator());
                break;
        }
        if (seed != -1) {
            worldCreator.seed(seed);
        }
        World world = worldCreator.createWorld();
        return world;
    }

    public String getName() {
        return name;
    }

    public static GeneratorType getFromString(String s) {
        GeneratorType generatorType = GeneratorType.FLAT;
        try {
            generatorType = GeneratorType.valueOf(s.toUpperCase());
        } catch (Exception e) {
        }
        return generatorType;
    }

    public GeneratorType last() {
        return values()[(this.ordinal() - 1 + values().length) % values().length];
    }

    public GeneratorType next() {
        return values()[(this.ordinal() + 1) % values().length];
    }
}