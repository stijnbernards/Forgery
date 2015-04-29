package com.stijnhero.forgery.common.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.stijnhero.forgery.recipes.ForgeryFurnaceRecipe;

public class TileEntityForgeryFurnace extends TileEntityForgery implements IUpdatePlayerListBox {

	private TileEntityHeater heater = null;
	private ForgeryFurnaceRecipe[] recipes;
	
	private double heat = 0.0;
	private double max_heat = 0.0;
	public int temperature = 0;

	public TileEntityForgeryFurnace() {
		this.inventory = new ItemStack[9];
		this.recipes = new ForgeryFurnaceRecipe[9];
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

	public void setHeaterTileEntity(TileEntityHeater heater) {
		this.heater = heater;
	}

	boolean f = true;
	@Override
	public void update() {
		if (!this.worldObj.isRemote) {
			if(this.heater == null && f){
				f = false;
				loadHeater(this.worldObj, this.pos);
			}
			if (this.heater != null) {
				this.heat = this.heater.getHeat();
				this.max_heat = this.heater.getMaxHeat();
			} else {
				this.heat = 0;
				this.max_heat = 0;
			}

			if (this.heat > this.max_heat) {
				this.heat = this.max_heat;
			}
		}

		if (this.heater != null) {
			this.temperature = (int)(this.heat / (this.max_heat / 100)); 
			this.handleCrafting();
		}
	}
	
	private void handleCrafting(){
		for(int i = 0; i < this.inventory.length; i++){
			if(this.inventory[i] != null && this.inventory[i].stackSize > 0){
				ForgeryFurnaceRecipe receip = ForgeryFurnaceRecipe.getRecipe(this.inventory[i]);
			}
		}
	}
	
	public static void loadHeater(World world, BlockPos pos){
		TileEntityForgeryFurnace tileentity = (TileEntityForgeryFurnace)world.getTileEntity(pos);
		TileEntity te = world.getTileEntity(pos.down(1));
		if(te instanceof TileEntityHeater){
			if(tileentity != null){
				tileentity.setHeaterTileEntity((TileEntityHeater)te);
			}
		}
		else{
			if(tileentity != null){
				tileentity.setHeaterTileEntity(null);
			}
		}
	}
	
	public double getHeat(){
		return this.heat;
	}
	
	public double getMaxHeat(){
		return this.max_heat;
	}
}
