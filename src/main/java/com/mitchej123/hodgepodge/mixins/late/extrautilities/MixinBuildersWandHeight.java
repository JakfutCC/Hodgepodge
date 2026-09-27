package com.mitchej123.hodgepodge.mixins.late.extrautilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.mitchej123.hodgepodge.util.CubicBlockBounds;
import com.rwtema.extrautils.item.ItemBuildersWand;

@Mixin(value = ItemBuildersWand.class, remap = false)
public abstract class MixinBuildersWandHeight {

    @ModifyConstant(method = "getPotentialBlocks", constant = @Constant(intValue = 255), require = 1)
    private int hodgepodge$placementCeiling(int original, EntityPlayer player, World world, int x, int y, int z,
            int face) {
        return CubicBlockBounds.maxHeight(world, original);
    }
}
