package net.pedroricardo.btsm;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.HudComponents;
import net.minecraft.client.gui.hud.SnapLayout;
import net.minecraft.client.render.Tessellator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class BTSM implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "btsm";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        LOGGER.info("BTSM initialized.");
		BTSMHudComponents.init();
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	protected static void drawTexturedModalRect(Gui gui, int x, int y, int u, int v, int width, int height) {
		float uScale = 1.0f/128.0f;
		float vScale = 1.0f/128.0f;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + height, gui.zLevel, (float)(u + 0) * uScale, (float)(v + height) * vScale);
		tessellator.addVertexWithUV(x + width, y + height, gui.zLevel, (float)(u + width) * uScale, (float)(v + height) * vScale);
		tessellator.addVertexWithUV(x + width, y + 0, gui.zLevel, (float)(u + width) * uScale, (float)(v + 0) * vScale);
		tessellator.addVertexWithUV(x + 0, y + 0, gui.zLevel, (float)(u + 0) * uScale, (float)(v + 0) * vScale);
		tessellator.draw();
	}
}
