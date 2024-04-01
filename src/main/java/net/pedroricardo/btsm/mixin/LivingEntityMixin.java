package net.pedroricardo.btsm.mixin;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.MathHelper;
import net.pedroricardo.btsm.BTSMPlayerDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityLiving.class, remap = false)
public class LivingEntityMixin {
	@Inject(method = "heal", at = @At("HEAD"))
	private void heal(int i, CallbackInfo ci) {
		if ((Object)this instanceof EntityPlayer) {
			((BTSMPlayerDuck)(Object)this).addStamina((byte)MathHelper.clamp(i / 2, 0, ((BTSMPlayerDuck)(Object)this).getMaxStamina()));
		}
	}
}
