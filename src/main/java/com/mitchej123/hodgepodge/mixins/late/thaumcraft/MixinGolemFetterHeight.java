package com.mitchej123.hodgepodge.mixins.late.thaumcraft;

import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

import com.mitchej123.hodgepodge.util.CubicBlockBounds;

import thaumcraft.common.entities.golems.EntityGolemBase;

@Mixin(EntityGolemBase.class)
public abstract class MixinGolemFetterHeight extends EntityGolem {

    protected MixinGolemFetterHeight(World world) {
        super(world);
    }

    @ModifyConstant(
            method = "onLivingUpdate",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/util/MathHelper;floor_double(D)I",
                            ordinal = 2),
                    to = @At(
                            value = "FIELD",
                            target = "Lthaumcraft/common/entities/golems/EntityGolemBase;inactive:Z",
                            opcode = Opcodes.PUTFIELD,
                            ordinal = 0,
                            remap = false)),
            constant = @Constant(
                    intValue = 0,
                    expandZeroConditions = Constant.Condition.LESS_THAN_OR_EQUAL_TO_ZERO,
                    ordinal = 0),
            require = 1)
    private int hodgepodge$fetterMinimum(int original) {
        return CubicBlockBounds.fetterThreshold(
                worldObj,
                MathHelper.floor_double(posX),
                MathHelper.floor_double(posY),
                MathHelper.floor_double(posZ),
                original);
    }
}
