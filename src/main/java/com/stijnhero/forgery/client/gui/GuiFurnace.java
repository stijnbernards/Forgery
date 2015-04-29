package com.stijnhero.forgery.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import com.stijnhero.forgery.client.gui.container.ContainerFurnace;
import com.stijnhero.forgery.common.tileentity.TileEntityFurnace;

public class GuiFurnace extends GuiContainer {

	public GuiFurnace(TileEntityFurnace tileentity, InventoryPlayer inventory_player) {
		super(new ContainerFurnace(tileentity, inventory_player));
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.drawDefaultBackground();
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
}
