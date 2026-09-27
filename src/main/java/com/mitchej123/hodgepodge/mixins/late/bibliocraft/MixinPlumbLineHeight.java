package com.mitchej123.hodgepodge.mixins.late.bibliocraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import jds.bibliocraft.items.ItemPlumbLine;

@Mixin(ItemPlumbLine.class)
public abstract class MixinPlumbLineHeight {

    @ModifyExpressionValue(
            method = "onItemRightClick",
            at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posY:D"),
            require = 1)
    private double hodgepodge$floorMeasurementY(double coordinate) {
        return Math.floor(coordinate);
    }
}
