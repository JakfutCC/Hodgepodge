package com.mitchej123.hodgepodge.mixins.late.advancedsolarpanels;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import advsolar.common.items.ItemAdvancedSolarHelmet;

@Mixin(value = ItemAdvancedSolarHelmet.class, remap = false)
public class MixinItemAdvancedSolarHelmet {

    @ModifyExpressionValue(
            method = "updateVisibility",
            at = { @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posX:D", remap = true),
                    @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posY:D", remap = true),
                    @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posZ:D", remap = true) },
            require = 5)
    private double hodgepodge$floorBlockCoordinate(double coordinate) {
        return Math.floor(coordinate);
    }
}
