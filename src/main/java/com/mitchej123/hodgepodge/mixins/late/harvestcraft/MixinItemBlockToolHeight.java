package com.mitchej123.hodgepodge.mixins.late.harvestcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.mitchej123.hodgepodge.util.CubicBlockBounds;
import com.pam.harvestcraft.ItemPamBlockTool;

@Mixin(ItemPamBlockTool.class)
public abstract class MixinItemBlockToolHeight {

    @ModifyConstant(method = "onItemUse", constant = @Constant(intValue = 255), require = 1)
    private int hodgepodge$solidCeiling(int original, ItemStack stack, EntityPlayer player, World world, int x, int y,
            int z, int side, float hitX, float hitY, float hitZ) {
        return CubicBlockBounds.maxHeight(world, original + 1) - 1;
    }
}
