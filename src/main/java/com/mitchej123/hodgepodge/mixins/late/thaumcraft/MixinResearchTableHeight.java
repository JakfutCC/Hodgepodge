package com.mitchej123.hodgepodge.mixins.late.thaumcraft;

import net.minecraft.tileentity.TileEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mitchej123.hodgepodge.util.CubicBlockBounds;

import thaumcraft.common.tiles.TileResearchTable;

@Mixin(value = TileResearchTable.class, remap = false)
public abstract class MixinResearchTableHeight extends TileEntity {

    @WrapMethod(method = "recalculateBonus")
    private void hodgepodge$waitForBonusArea(Operation<Void> original) {
        if (CubicBlockBounds.researchAreaLoaded(worldObj, xCoord, yCoord, zCoord)) original.call();
    }

    @ModifyConstant(
            method = "recalculateBonus",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lthaumcraft/api/aspects/AspectList;merge(Lthaumcraft/api/aspects/Aspect;I)Lthaumcraft/api/aspects/AspectList;",
                            ordinal = 3),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/World;getBlock(III)Lnet/minecraft/block/Block;",
                            ordinal = 0,
                            remap = true)),
            constant = @Constant(
                    intValue = 0,
                    expandZeroConditions = Constant.Condition.LESS_THAN_OR_EQUAL_TO_ZERO,
                    ordinal = 0),
            require = 1)
    private int hodgepodge$bonusMinimum(int original) {
        return CubicBlockBounds.researchMinimum(worldObj, original);
    }

    @ModifyExpressionValue(
            method = "recalculateBonus",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getActualHeight()I",
                    ordinal = 3,
                    remap = true),
            require = 1)
    private int hodgepodge$bonusMaximum(int original) {
        return CubicBlockBounds.maxHeight(worldObj, original);
    }
}
