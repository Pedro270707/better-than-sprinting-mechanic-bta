package net.pedroricardo.btsm.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import net.pedroricardo.btsm.BTSMPlayerDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(value = World.class, remap = false)
public class WorldMixin {
	@SuppressWarnings("UnresolvedMixinReference")
	@WrapOperation(method = "wakeUpAllPlayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/EntityPlayer;wakeUpPlayer(ZZ)V"))
	private void btsm$notTooTired(EntityPlayer instance, boolean b, boolean flag, Operation<Void> original) {
		((BTSMPlayerDuck)instance).setTooTired(false);
		original.call(instance, b, flag);
	}
}
