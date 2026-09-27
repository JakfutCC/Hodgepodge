package com.mitchej123.hodgepodge.util;

import net.minecraft.world.World;

import com.cardinalstar.cubicchunks.world.ICubicWorld;
import com.cardinalstar.cubicchunks.world.cube.ICubeProvider;
import com.mitchej123.hodgepodge.Compat;

public final class CubicBlockBounds {

    private CubicBlockBounds() {}

    public static boolean isCubic(World world) {
        return Compat.isCubicChunksPresent() && Cubic.isCubic(world);
    }

    public static int maxHeight(World world, int fallback) {
        return isCubic(world) ? Cubic.maxHeight(world) : fallback;
    }

    public static int researchMinimum(World world, int fallback) {
        return isCubic(world) ? (int) Math.max(Integer.MIN_VALUE, (long) Cubic.minHeight(world) - 1) : fallback;
    }

    public static boolean researchAreaLoaded(World world, int x, int y, int z) {
        if (!isCubic(world)) return true;
        if (x < Integer.MIN_VALUE + 8 || x > Integer.MAX_VALUE - 8
                || z < Integer.MIN_VALUE + 8
                || z > Integer.MAX_VALUE - 8
                || y < Integer.MIN_VALUE + 8
                || y > Integer.MAX_VALUE - 8)
            return false;
        int min = Cubic.minHeight(world);
        int max = Cubic.maxHeight(world);
        if (y < min || y >= max) return false;
        return world.checkChunksExist(
                x - 8,
                Math.max(y - 8, min),
                z - 8,
                x + 8,
                (int) Math.min((long) y + 8, (long) max - 1),
                z + 8);
    }

    public static int fetterThreshold(World world, int x, int feetY, int z, int fallback) {
        if (!isCubic(world)) return fallback;
        long below = (long) feetY - 1;
        if (below < Cubic.minHeight(world) || below >= Cubic.maxHeight(world)
                || !world.checkChunksExist(x, (int) below, z, x, (int) below, z)) {
            return Integer.MAX_VALUE;
        }
        return Cubic.minHeight(world);
    }

    private static final class Cubic {

        private static boolean isCubic(World world) {
            return world instanceof ICubicWorld && world.getChunkProvider() instanceof ICubeProvider;
        }

        private static int minHeight(World world) {
            return ((ICubicWorld) world).getMinHeight();
        }

        private static int maxHeight(World world) {
            return ((ICubicWorld) world).getMaxHeight();
        }
    }
}
