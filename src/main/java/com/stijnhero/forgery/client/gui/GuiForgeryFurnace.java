package com.stijnhero.forgery.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.stijnhero.forgery.ForgeryBlocks;
import com.stijnhero.forgery.client.gui.container.ContainerForgeryFurnace;
import com.stijnhero.forgery.common.tileentity.TileEntityForgeryFurnace;

public class GuiForgeryFurnace extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation("forgery", "gui/forgery_furnace.png");
	private TileEntityForgeryFurnace tileentity;
	
	public GuiForgeryFurnace(TileEntityForgeryFurnace tileentity, InventoryPlayer inventory_player) {
		super(new ContainerForgeryFurnace(tileentity, inventory_player));
		this.tileentity = tileentity;
		this.xSize = 176;
		this.ySize = 195;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(texture);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(texture);
		this.drawTexturedModalRect(8, 46, 176, 0, 3, 46);
		this.drawTexturedModalRect(165, 46, 176, 0, 3, 46);
		
		this.drawTexturedModalRect(5 + this.tileentity.temperature, 101, 8, 195, 5, 7);
	}
}