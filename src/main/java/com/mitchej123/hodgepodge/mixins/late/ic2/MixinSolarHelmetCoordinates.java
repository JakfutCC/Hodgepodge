package com.mitchej123.hodgepodge.mixins.late.ic2;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import ic2.core.item.armor.ItemArmorSolarHelmet;

@Mixin(value = ItemArmorSolarHelmet.class, remap = false)
public class MixinSolarHelmetCoordinates {

    @ModifyExpressionValue(
            method = "onArmorTick",
            at = { @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posX:D", remap = true),
                    @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posY:D", remap = true),
                    @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posZ:D", remap = true) },
            require = 3)
    private double hodgepodge$floorBlockCoordinate(double coordinate) {
        return Math.floor(coordinate);
    }
}
