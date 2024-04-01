package net.pedroricardo.btsm;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import net.minecraft.client.render.Tessellator;
import org.lwjgl.opengl.GL11;

public class StaminaBarComponent extends MovableHudComponent {
	public StaminaBarComponent(String key, Layout layout) {
		super(key, 81, 10, layout);
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
		int stamina = (int)((BTSMPlayerDuck)mc.thePlayer).getStamina();
		for (int i = 0; i < 10; ++i) {
			int xStamina = x + this.getXSize(mc) - i * 8 - 9;
			if (i * 2 + 1 < stamina) {
				BTSM.drawTexturedModalRect(gui, xStamina, y, 18, 0, 9, 9);
			}
			if (i * 2 + 1 == stamina) {
				BTSM.drawTexturedModalRect(gui, xStamina, y, 9, 0, 9, 9);
			}
			if (i * 2 + 1 <= stamina) continue;
			BTSM.drawTexturedModalRect(gui, xStamina, y, 0, 0, 9, 9);
		}
	}

	@Override
	public void renderPreview(Minecraft mc, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
		int x = layout.getComponentX(mc, this, xSizeScreen);
		int y = layout.getComponentY(mc, this, ySizeScreen);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glBindTexture(3553, mc.renderEngine.getTexture("/assets/btsm/gui/icons.png"));
		GL11.glDisable(3042);
		int stamina = 11;
		for (int i = 0; i < 10; ++i) {
			int xStamina = x + this.getXSize(mc) - i * 8 - 9;
			if (i * 2 + 1 < stamina) {
				BTSM.drawTexturedModalRect(gui, xStamina, y, 9, 0, 9, 9);
			}
			if (i * 2 + 1 == stamina) {
				BTSM.drawTexturedModalRect(gui, xStamina, y, 9, 0, 9, 9);
			}
			if (i * 2 + 1 <= stamina) continue;
			BTSM.drawTexturedModalRect(gui, xStamina, y, 0, 0, 9, 9);
		}
	}
}
