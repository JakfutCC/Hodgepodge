package com.mitchej123.hodgepodge.mixins.late.ic2;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mitchej123.hodgepodge.util.CubicSolarLight;

import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;

@Mixin(value = TileEntitySolarGenerator.class, remap = false)
public class MixinSolarGeneratorCoordinates extends TileEntity {

    @WrapOperation(
            method = "gainEnergy",
            at = @At(
                    value = "INVOKE",
                    target = "Lic2/core/block/generator/tileentity/TileEntitySolarGenerator;getskylight(Lnet/minecraft/world/World;II)I"),
            require = 1)
    private int hodgepodge$samplePanelHeight(World world, int x, int z, Operation<Integer> original) {
        return CubicSolarLight.sample(world, x, (long) yCoord + 1, z, () -> original.call(world, x, z));
    }

    @WrapOperation(
            method = "isSunVisible",
            at = @At(
                    value = "INVOKE",
                    target = "Lic2/core/block/generator/tileentity/TileEntitySolarGenerator;getskylight(Lnet/minecraft/world/World;II)I"),
            require = 1)
    private static int hodgepodge$sampleVisibilityHeight(World world, int x, int z, Operation<Integer> original,
            World methodWorld, int methodX, int methodY, int methodZ) {
        return CubicSolarLight.sample(world, x, methodY, z, () -> original.call(world, x, z));
    }
}
