package net.pedroricardo.btsm;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import org.lwjgl.opengl.GL11;

import java.util.function.Function;
import java.util.function.Supplier;

public class BooleanConditionComponent extends MovableHudComponent {
	private final int falseX;
	private final int falseY;
	private final int trueX;
	private final int trueY;
	private final Function<Minecraft, Boolean> booleanFunction;

	public BooleanConditionComponent(String key, Layout layout, int falseX, int falseY, int trueX, int trueY, Function<Minecraft, Boolean> booleanFunction) {
		super(key, 10, 10, layout);
		this.falseX = falseX;
		this.falseY = falseY;
		this.trueX = trueX;
		this.trueY = trueY;
		this.booleanFunction = booleanFunction;
	}

	@Override
	public boolean isVisible(Minecraft minecraft) {
		return !minecraft.thePlayer.getGamemode().canPlayerFly() && minecraft.gameSettings.immersiveMode.drawHotbar();
	}

	@Override
	public void render(Minecraft mc, GuiIngame gui, int xSizeScreen, int ySizeScreen, float partialTick) {
		int x = this.getLayout().getComponentX(mc, this, xSizeScreen);
		int y = this.getLayout().getComponentY(mc, this, ySizeScreen);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glBindTexture(3553, mc.renderEngine.getTexture("/assets/btsm/gui/icons.png"));
		GL11.glDisable(3042);
		boolean value = this.booleanFunction.apply(mc);
		BTSM.drawTexturedModalRect(gui, x + this.getXSize(mc) - 9, y, value ? this.trueX : this.falseX, value ? this.trueY : this.falseY, 9, 9);
	}

	@Override
	public void renderPreview(Minecraft mc, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
		int x = this.getLayout().getComponentX(mc, this, xSizeScreen);
		int y = this.getLayout().getComponentY(mc, this, ySizeScreen);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glBindTexture(3553, mc.renderEngine.getTexture("/assets/btsm/gui/icons.png"));
		GL11.glDisable(3042);
		BTSM.drawTexturedModalRect(gui, x + this.getXSize(mc) - 9, y, this.trueX, this.trueY, 9, 9);
	}
}
