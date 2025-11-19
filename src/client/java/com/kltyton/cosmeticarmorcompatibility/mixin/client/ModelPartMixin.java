package com.kltyton.cosmeticarmorcompatibility.mixin.client;

import io.github.apace100.cosmetic_armor.CosmeticArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(value = Player.class, priority = 0)
public abstract class ModelPartMixin extends LivingEntity {
    protected ModelPartMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "getItemBySlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EquipmentSlot;getType()Lnet/minecraft/world/entity/EquipmentSlot$Type;"), cancellable = true)
    public void getItemBySlot(EquipmentSlot equipmentSlot, CallbackInfoReturnable<ItemStack> cir) {
        if (this.level().isClientSide() && equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
            ItemStack cosmeticStack = CosmeticArmor.getCosmeticArmor((Player) (Object) this, equipmentSlot);
            if (!cosmeticStack.isEmpty()) {
                cir.setReturnValue(cosmeticStack);
                cir.cancel();
            }
        }
    }

}
