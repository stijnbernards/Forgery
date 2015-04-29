package com.stijnhero.forgery.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TileEntityForgeryFurnace extends TileEntityForgery implements IUpdatePlayerListBox {

	private TileEntityHeater heater = null;
	
	public TileEntityForgeryFurnace() {
		this.inventory = new ItemStack[9];
	}
	
	@Override
	public String getName() {
		return "forgery.furnace";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}
	
	public void setHeaterTileEntity(TileEntityHeater heater){
		this.heater = heater;
	}

	@Override
	public void update() {
		System.out.println("UPDATIGN");
		if(this.heater != null){
			System.out.println(this.heater.getHeat() + "/" + this.heater.getMaxHeat());
		}
	}
}
