package de.hyper.worlds.common.obj.generators;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Random;

public class VoidChunkGenerator extends ChunkGenerator {

    @NotNull
    @Override
    public ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int x, int z, @NotNull ChunkGenerator.BiomeGrid biome) {
        return createChunkData(world);
    }
}
