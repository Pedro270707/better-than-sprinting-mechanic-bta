package net.pedroricardo.btsm;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.HudComponent;
import net.minecraft.client.gui.hud.HudComponents;
import net.minecraft.client.gui.hud.SnapLayout;

public class BTSMHudComponents {
	public static final HudComponent STAMINA_BAR = HudComponents.register(new StaminaBarComponent("stamina_bar", new SnapLayout(HudComponents.ARMOR_BAR, ComponentAnchor.TOP_RIGHT, ComponentAnchor.BOTTOM_RIGHT)));
	public static final HudComponent IS_HUNGRY = HudComponents.register(new BooleanConditionComponent("is_hungry", new SnapLayout(HudComponents.HOTBAR, ComponentAnchor.TOP_RIGHT, ComponentAnchor.TOP_LEFT), 27, 0, 36, 0, (mc) -> mc.thePlayer != null && ((BTSMPlayerDuck)mc.thePlayer).isHungry()));
	public static final HudComponent IS_WRONG_TEMPERATURE = HudComponents.register(new BooleanConditionComponent("is_wrong_temperature", new SnapLayout(HudComponents.HOTBAR, ComponentAnchor.BOTTOM_RIGHT, ComponentAnchor.BOTTOM_LEFT), 45, 0, 54, 0, (mc) -> mc.thePlayer != null && ((BTSMPlayerDuck)mc.thePlayer).isWrongTemperature()));
	public static final HudComponent ARE_LEGS_DESTROYED = HudComponents.register(new BooleanConditionComponent("are_legs_destroyed", new SnapLayout(IS_HUNGRY, ComponentAnchor.TOP_RIGHT, ComponentAnchor.TOP_LEFT), 63, 0, 72, 0, (mc) -> mc.thePlayer != null && ((BTSMPlayerDuck)mc.thePlayer).areLegsDestroyed()));
	public static final HudComponent IS_TOO_TIRED = HudComponents.register(new BooleanConditionComponent("is_too_tired", new SnapLayout(IS_WRONG_TEMPERATURE, ComponentAnchor.BOTTOM_RIGHT, ComponentAnchor.BOTTOM_LEFT), 81, 0, 90, 0, (mc) -> mc.thePlayer != null && ((BTSMPlayerDuck)mc.thePlayer).isTooTired()));
	public static final HudComponent IS_SICK = HudComponents.register(new BooleanConditionComponent("is_sick", new SnapLayout(ARE_LEGS_DESTROYED, ComponentAnchor.TOP_RIGHT, ComponentAnchor.TOP_LEFT), 99, 0, 108, 0, (mc) -> mc.thePlayer != null && ((BTSMPlayerDuck)mc.thePlayer).isSick()));
	public static final HudComponent IS_THIRSTY = HudComponents.register(new BooleanConditionComponent("is_thirsty", new SnapLayout(IS_TOO_TIRED, ComponentAnchor.BOTTOM_RIGHT, ComponentAnchor.BOTTOM_LEFT), 117, 0, 0, 9, (mc) -> mc.thePlayer != null && ((BTSMPlayerDuck)mc.thePlayer).isThirsty()));
	public static final HudComponent IS_FIVE = HudComponents.register(new BooleanConditionComponent("is_five", new SnapLayout(IS_SICK, ComponentAnchor.TOP_RIGHT, ComponentAnchor.TOP_LEFT), 9, 9, 18, 9, (mc) -> mc.thePlayer != null && ((BTSMPlayerDuck)mc.thePlayer).isFive()));
	public static final HudComponent IS_TOO_DARK = HudComponents.register(new BooleanConditionComponent("is_too_dark", new SnapLayout(IS_THIRSTY, ComponentAnchor.BOTTOM_RIGHT, ComponentAnchor.BOTTOM_LEFT), 27, 9, 36, 9, (mc) -> mc.thePlayer != null && ((BTSMPlayerDuck)mc.thePlayer).isTooDark()));
	public static final HudComponent IS_GAME_TOO_SLOW = HudComponents.register(new BooleanConditionComponent("is_game_too_slow", new SnapLayout(IS_FIVE, ComponentAnchor.TOP_RIGHT, ComponentAnchor.TOP_LEFT), 45, 9, 54, 9, (mc) -> mc.thePlayer != null && ((BTSMPlayerDuck)mc.thePlayer).gameTooSlow()));
	public static final HudComponent IS_UNDER_RAIN = HudComponents.register(new BooleanConditionComponent("is_under_rain", new SnapLayout(IS_TOO_DARK, ComponentAnchor.BOTTOM_RIGHT, ComponentAnchor.BOTTOM_LEFT), 63, 9, 72, 9, (mc) -> mc.thePlayer != null && ((BTSMPlayerDuck)mc.thePlayer).isUnderRain()));
	public static final HudComponent IS_TOO_HEAVY = HudComponents.register(new BooleanConditionComponent("is_too_heavy", new SnapLayout(IS_GAME_TOO_SLOW, ComponentAnchor.TOP_RIGHT, ComponentAnchor.TOP_LEFT), 81, 9, 90, 9, (mc) -> mc.thePlayer != null && ((BTSMPlayerDuck)mc.thePlayer).isTooHeavy()));
	public static final HudComponent IS_LOOKING_UP = HudComponents.register(new BooleanConditionComponent("is_looking_up", new SnapLayout(IS_UNDER_RAIN, ComponentAnchor.BOTTOM_RIGHT, ComponentAnchor.BOTTOM_LEFT), 99, 9, 108, 9, (mc) -> mc.thePlayer != null && ((BTSMPlayerDuck)mc.thePlayer).isLookingUp()));

	public static void init() {
		BTSM.LOGGER.debug("Registering HUD components");
	}
}
