package com.mitchej123.hodgepodge.util;

import java.util.function.IntSupplier;

import net.minecraft.world.World;

import com.cardinalstar.cubicchunks.world.ICubicWorld;
import com.cardinalstar.cubicchunks.world.cube.ICubeProvider;
import com.mitchej123.hodgepodge.Compat;

public final class CubicSolarLight {

    private CubicSolarLight() {}

    public static int sample(World world, int x, long y, int z, IntSupplier original) {
        if (!Compat.isCubicChunksPresent()) return original.getAsInt();
        return CubicAdapter.sample(world, x, y, z, original);
    }

    private static final class CubicAdapter {

        private static int sample(World world, int x, long y, int z, IntSupplier original) {
            if (!(world.getChunkProvider() instanceof ICubeProvider)) return original.getAsInt();
            ICubicWorld cubic = (ICubicWorld) world;
            if (y < cubic.getMinHeight() || y >= cubic.getMaxHeight()) return 0;
            if (x == Integer.MIN_VALUE || x == Integer.MAX_VALUE || z == Integer.MIN_VALUE || z == Integer.MAX_VALUE)
                return 0;
            // Neighbor-brightness blocks also sample above and on all four horizontal sides.
            int maxY = (int) Math.min(y + 1, (long) cubic.getMaxHeight() - 1);
            if (!world.checkChunksExist(x - 1, (int) y, z - 1, x + 1, maxY, z + 1)) return 0;
            return world.getBlockLightValue(x, (int) y, z);
        }
    }
}
