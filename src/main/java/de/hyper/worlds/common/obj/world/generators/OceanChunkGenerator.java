package de.hyper.worlds.common.obj.world.generators;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class OceanChunkGenerator extends ChunkGenerator {

    @NotNull
    @Override
    public ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int x, int z,
                                       @NotNull ChunkGenerator.BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);
        for (int X = 0; X < 16; X++) {
            for (int Z = 0; Z < 16; Z++) {
                for (int Y = -64; Y < 0; Y++) {
                    chunk.setBlock(X, Y, Z, getMaterialY(Y));
                }
            }
        }
        return chunk;
    }

    public Material getMaterialY(int y) {
        Material mat = Material.WATER;
        if (y == -64) {
            mat = Material.BEDROCK;
        } else if (y == -63) {
            mat = Material.STONE;
        } else if (y == -62 || y== -61) {
            mat = Material.GRAVEL;
        } else if (y > -61 && y < 1) {
            mat = Material.WATER;
        }
        return mat;
    }
}