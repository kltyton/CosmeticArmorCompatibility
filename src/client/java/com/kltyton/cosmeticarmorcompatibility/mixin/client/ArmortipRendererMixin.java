package com.kltyton.cosmeticarmorcompatibility.mixin.client;

import io.github.apace100.cosmetic_armor.CosmeticArmor;
import net.bmjo.armortip.client.gui.ArmortipRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(ArmortipRenderer.class)
public class ArmortipRendererMixin {
    @Redirect(method = "renderEntityWithArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack getItemBySlot(Player instance, EquipmentSlot equipmentSlot) {
        ItemStack cosmeticStack = CosmeticArmor.getCosmeticArmor(instance, equipmentSlot);
        if (!cosmeticStack.isEmpty()) {
            return instance.getInventory().getArmor(equipmentSlot.getIndex());
        }
        return instance.getItemBySlot(equipmentSlot);
    }
}
