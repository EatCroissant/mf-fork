package minefantasy.mf2.commands;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public interface WorldGenMFBase extends IWorldGenerator {
    void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
                  IChunkProvider chunkProvider);
}
