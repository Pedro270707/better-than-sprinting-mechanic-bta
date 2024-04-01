package net.pedroricardo.btsm.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.pedroricardo.btsm.BTSMPlayerDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemFood.class, remap = false)
public class EatingMixin {
	@WrapOperation(method = "onItemRightClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/EntityPlayer;getHealth()I"))
	private int btsm$alsoEatWhenStaminaIsLow(EntityPlayer instance, Operation<Integer> original) {
		//noinspection ConstantValue
		if (((BTSMPlayerDuck)instance).getStamina() < ((BTSMPlayerDuck)instance).getMaxStamina() || ((BTSMPlayerDuck)instance).isHungry() || (((BTSMPlayerDuck)instance).isSick() && (Object)this == Item.foodAppleGold)) {
			return Integer.MIN_VALUE;
		}
		return original.call(instance);
	}

	@Inject(method = "onItemRightClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/EntityPlayer;heal(I)V"))
	private void btsm$notHungryAnymore(ItemStack itemstack, World world, EntityPlayer entityplayer, CallbackInfoReturnable<ItemStack> cir) {
		((BTSMPlayerDuck)entityplayer).setHungry(false);
		//noinspection ConstantValue
		if ((Object)this == Item.foodAppleGold) {
			((BTSMPlayerDuck)entityplayer).setSick(false);
		}
	}
}
