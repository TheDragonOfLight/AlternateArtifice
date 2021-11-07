package dragon.altarti.worldgen;

import dragon.altarti.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;
import java.util.function.Predicate;

public class OreGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case -1:
                break;
            case 0:
                runGenerator(ModBlocks.oreTopaz.getDefaultState(), 1, 1, 5,
                        15, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ);
                break;
            case 1:
                break;
            default:
                break;
        }
    }

    private void runGenerator(IBlockState blockToGen, int blockAmount, int chancesToSpawn, int minHeight, int maxHeight,
                              Predicate<IBlockState> blockToReplace, World world, Random rand, int chunk_X, int chunk_Z) {
        if (minHeight > maxHeight || minHeight < 0 || maxHeight > 256) {throw new IllegalArgumentException("Ore generated out of bounds.");}

        int heightDiff = maxHeight - minHeight + 1;

        for (int i = 0; i < chancesToSpawn; i++) {
            int x = chunk_X * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightDiff);
            int z = chunk_Z * 16 + rand.nextInt(16);

            world.setBlockState(new BlockPos(x, y, z), ModBlocks.oreTopaz.getDefaultState());
        }
    }
}
