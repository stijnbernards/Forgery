package com.stijnhero.forgery.client.gui;

import java.util.Random;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;

import org.lwjgl.opengl.GL11;

import com.stijnhero.forgery.client.gui.container.ContainerForgeryFurnace;
import com.stijnhero.forgery.common.tileentity.TileEntityForgeryFurnace;

public class GuiForgeryFurnace extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation("forgery", "textures/gui/forgery_furnace.png");
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
	
	Random random = new Random();
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GL11.glColor4f(1, 1, 1, 1);
		this.mc.renderEngine.bindTexture(texture);
		
		this.drawTexturedModalRect(5 + this.tileentity.temperature, 101, 8, 195, 5, 7);
		
		for(int i = 0; i < 9; i++){
			int c = 0;
			if(this.tileentity.durations[i] > 0){
				c = this.tileentity.getCraftingProgressScaled(i, 15);
			}
			this.drawTexturedModalRect(12 + i * 18, 26, 0, 195, 8, c);
		}
		
		int s = 0;
		for(FluidTank tank : this.tileentity.tanks.values()){
			Fluid fluid = tank.getFluid().getFluid();
			int height = tank.getFluidAmount() * 52 / TileEntityForgeryFurnace.MAX_TANK + 1;
			if(fluid.getName().equals("fluidliquidcopper")){
				GL11.glColor4f(1, 0, 1, 1);
			}
			
			if(fluid.getName().equals("fluidliquidtin")){
				GL11.glColor4f(0, 0, 1, 1);
			}
			this.drawTexturedModalRect(8, 96 - height - s, 16, 0, 160, height);
			s += height;
		}
		
		System.out.println(this.tileentity.getCurrentFluid() + "/" + TileEntityForgeryFurnace.MAX_TANK);
		
		this.drawTexturedModalRect(8, 46, 176, 0, 3, 46);
		this.drawTexturedModalRect(165, 46, 176, 0, 3, 46);
	}
}
