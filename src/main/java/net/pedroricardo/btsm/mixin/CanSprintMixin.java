package net.pedroricardo.btsm.mixin;

import com.mojang.nbt.CompoundTag;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.weather.Weather;
import net.pedroricardo.btsm.BTSMPlayerDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Mixin(value = EntityPlayer.class, remap = false)
public class CanSprintMixin implements BTSMPlayerDuck {
	@Unique
	private float stamina;

	@Unique
	private boolean isBiomeTemperatureWrong = false;

	@Unique
	private boolean areLegsDestroyed = false;

	@Unique
	private boolean isTooTired = false;

	@Unique
	private boolean isHungry = false;

	@Unique
	private boolean isSick = false;

	@Unique
	private boolean isThirsty = false;

	@Unique
	private boolean isFive = false;

	@Unique
	private boolean isTooDark = false;

	@Unique
	private boolean isUnderRain = false;

	@Unique
	private boolean isTooHeavy = false;

	@Environment(EnvType.CLIENT)
	@Unique
	private boolean gameTooSlow = false;

	@Unique
	private static final List<String> trippingPossibilities = Arrays.asList("rock", "twig", "thing", "stop");

	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo ci) {
		EntityPlayer player = (EntityPlayer)(Object)this;
		if (player.getGamemode().canPlayerFly()) return;
		if (player.world.getBlockBiome((int)player.x, (int)player.y, (int)player.z).hasSurfaceSnow()) {
			if (!this.isWrongTemperature()) {
				player.addChatMessage("btsm.tooCold");
				this.isBiomeTemperatureWrong = true;
			}
		} else if (isTooHot(player)) {
			if (!this.isWrongTemperature()) {
				player.addChatMessage("btsm.tooHot");
				this.isBiomeTemperatureWrong = true;
			}
			if (player.world.rand.nextFloat() < 0.0078125f) {
				this.isThirsty = true;
			}
		} else {
			this.isBiomeTemperatureWrong = false;
		}
		if (player.getHealth() == player.getMaxHealth()) {
			this.areLegsDestroyed = false;
		}
		if (!player.world.isDaytime() && !this.isTooTired()) {
			player.addChatMessage("btsm.tooTired");
			this.setTooTired(true);
		}
		if (player.isSprinting() && this.getStamina() > 0 && player.world.rand.nextFloat() < 0.125f) {
			this.addStamina(-0.125f);
		}
		if (player.isSprinting() && player.world.rand.nextFloat() < 0.001953125f) {
			player.addChatMessage("btsm.tripped." + trippingPossibilities.get((int)(player.world.rand.nextFloat() * 4)));
			this.setStamina((byte)0);
		}
		if (this.getStamina() == this.getMaxStamina() && !this.isHungry() && player.world.rand.nextFloat() < 0.001953125f) {
			player.addChatMessage("btsm.hungry");
			this.setHungry(true);
		}
		if (!this.isSick() && player.world.rand.nextFloat() < 0.0001220703125f) {
			player.addChatMessage("btsm.sick");
			this.setSick(true);
		}
		if (player.isUnderLiquid(Material.water)) {
			this.setThirsty(false);
		} else if (player.isSprinting() && !this.isThirsty() && player.world.rand.nextFloat() < 0.001953125f) {
			player.addChatMessage("btsm.thirsty");
			this.setThirsty(true);
		}
		if (player.isSprinting() && player.world.rand.nextFloat() < 0.001953125f) {
			this.setFive(!this.isFive());
		}
		if (player.world.getBlockLightValue((int)player.x, (int)player.y, (int)player.z) <= 5) {
			if (!this.isTooDark()) {
				player.addChatMessage("btsm.tooDark");
				this.setTooDark(true);
			}
		} else {
			this.setTooDark(false);
		}
		if (player.world.canBlockBeRainedOn(MathHelper.floor_double(player.x), MathHelper.floor_double(player.y), MathHelper.floor_double(player.z))) {
			if (!this.isUnderRain()) {
				player.addChatMessage("btsm.underRain");
				this.setUnderRain(true);
			}
		} else {
			this.setUnderRain(false);
		}
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && Integer.parseInt(Minecraft.getMinecraft(Minecraft.class).debugFPS.substring(0, Minecraft.getMinecraft(Minecraft.class).debugFPS.length() - 4)) < 30) {
			if (!this.gameTooSlow) {
				Minecraft.getMinecraft(Minecraft.class).ingameGUI.addChatMessage(I18n.getInstance().translateKey("btsm.gameTooSlow"));
				this.gameTooSlow = true;
			}
		} else {
			this.gameTooSlow = false;
		}
		int amountOfItems = 0;
		for (ItemStack itemStack : player.inventory.mainInventory) {
			amountOfItems += itemStack == null ? 0 : itemStack.stackSize;
		}
		if (player.inventory.getTotalArmourPoints() > 3 || amountOfItems > 256) {
			if (!this.isTooHeavy()) {
				player.addChatMessage("btsm.tooHeavy");
				this.setTooHeavy(true);
			}
		} else {
			this.setTooHeavy(false);
		}
	}

	@Unique
	private static boolean isTooHot(EntityPlayer player) {
		for (Weather weather : player.world.getBlockBiome((int)player.x, (int)player.y, (int)player.z).blockedWeathers) {
			if (weather.precipitationType == 0) {
				return true;
			}
		}
		return false;
	}

	@Inject(method = "causeFallDamage", at = @At("HEAD"))
	private void causeFallDamage(float f, CallbackInfo ci) {
		EntityPlayer player = (EntityPlayer)(Object)this;
		if (Math.ceil(f - 3.0F) > 0.5f && player.getHealth() == player.getMaxHealth() && !this.areLegsDestroyed() && !player.getGamemode().isPlayerInvulnerable()) {
			player.addChatMessage("btsm.brokenLeg");
			this.setLegsDestroyed(true);
		}
	}

	@Inject(method = "onLivingUpdate", at = @At("HEAD"))
	private void stopSprinting(CallbackInfo ci) {
		EntityPlayer player = (EntityPlayer)(Object)this;
		if (!player.getGamemode().canPlayerFly() && (this.isWrongTemperature() || this.areLegsDestroyed() || this.isTooTired() || this.getStamina() == 0 || this.isHungry() || this.isSick() || this.isThirsty() || this.isTooDark() || this.isUnderRain() || this.isTooHeavy() || this.isLookingUp() || (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && this.gameTooSlow))) {
			player.setSprinting(false);
		}
	}

	@Override
	public void setTooTired(boolean value) {
		this.isTooTired = value;
	}

	@Override
	public void addStamina(float value) {
		this.stamina = MathHelper.clamp(this.stamina + value, 0, this.getMaxStamina());
	}

	@Override
	public void setStamina(float value) {
		this.stamina = MathHelper.clamp(value, 0, this.getMaxStamina());
	}

	@Override
	public float getStamina() {
		return this.stamina;
	}

	@Override
	public float getMaxStamina() {
		return 20;
	}

	@Override
	public boolean isHungry() {
		return this.isHungry;
	}

	@Override
	public void setHungry(boolean value) {
		this.isHungry = value;
	}

	@Override
	public boolean isWrongTemperature() {
		return this.isBiomeTemperatureWrong;
	}

	@Override
	public boolean areLegsDestroyed() {
		return this.areLegsDestroyed;
	}

	@Override
	public void setLegsDestroyed(boolean value) {
		this.areLegsDestroyed = true;
	}

	@Override
	public boolean isTooTired() {
		return this.isTooTired;
	}

	@Override
	public boolean isSick() {
		return this.isSick;
	}

	@Override
	public void setSick(boolean value) {
		this.isSick = value;
	}

	@Override
	public boolean isThirsty() {
		return this.isThirsty;
	}

	@Override
	public void setThirsty(boolean value) {
		this.isThirsty = value;
	}

	@Override
	public boolean isFive() {
		return this.isFive;
	}

	@Override
	public void setFive(boolean value) {
		this.isFive = value;
	}

	@Override
	public boolean isTooDark() {
		return this.isTooDark;
	}

	@Override
	public void setTooDark(boolean value) {
		this.isTooDark = value;
	}

	@Override
	public boolean isUnderRain() {
		return this.isUnderRain;
	}

	@Override
	public void setUnderRain(boolean value) {
		this.isUnderRain = value;
	}

	@Override
	public boolean isTooHeavy() {
		return this.isTooHeavy;
	}

	@Override
	public void setTooHeavy(boolean value) {
		this.isTooHeavy = value;
	}

	@Override
	public boolean isLookingUp() {
		return (((EntityPlayer)(Object)this).xRot % 360.0f) < -25.0f;
	}

	@Override
	public boolean gameTooSlow() {
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
			return false;
		} else {
			return this.gameTooSlow;
		}
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	private void btsm$addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
		tag.putFloat("Stamina", this.stamina);
		tag.putBoolean("IsBiomeTemperatureWrong", this.isBiomeTemperatureWrong);
		tag.putBoolean("AreLegsDestroyed", this.areLegsDestroyed);
		tag.putBoolean("IsTooTired", this.isTooTired);
		tag.putBoolean("IsHungry", this.isHungry);
		tag.putBoolean("IsSick", this.isSick);
		tag.putBoolean("IsThirsty", this.isThirsty);
		tag.putBoolean("IsFive", this.isFive);
		tag.putBoolean("IsTooDark", this.isTooDark);
		tag.putBoolean("IsUnderRain", this.isUnderRain);
		tag.putBoolean("IsTooHeavy", this.isTooHeavy);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	private void btsm$readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
		this.stamina = tag.getFloat("Stamina");
		this.isBiomeTemperatureWrong = tag.getBoolean("IsBiomeTemperatureWrong");
		this.areLegsDestroyed = tag.getBoolean("AreLegsDestroyed");
		this.isTooTired = tag.getBoolean("IsTooTired");
		this.isHungry = tag.getBoolean("IsHungry");
		this.isThirsty = tag.getBoolean("IsThirsty");
		this.isFive = tag.getBoolean("IsFive");
		this.isTooDark = tag.getBoolean("IsTooDark");
		this.isUnderRain = tag.getBoolean("IsUnderRain");
		this.isTooHeavy = tag.getBoolean("IsTooHeavy");
	}
}
