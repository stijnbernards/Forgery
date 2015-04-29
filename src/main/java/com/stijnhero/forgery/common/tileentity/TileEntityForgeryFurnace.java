package com.stijnhero.forgery.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;

public class TileEntityForgeryFurnace extends TileEntity implements IInventory, IUpdatePlayerListBox {

	private ItemStack[] inventory = new ItemStack[8];
	private TileEntityHeater heater = null;
	
	@Override
	public String getName() {
		return "forgery.furnace";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return null;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = getStackInSlot(index);
		if (stack != null) {
			if (stack.stackSize <= count) {
				setInventorySlotContents(index, null);
			} else {
				stack = stack.splitStack(count);
				if (stack.stackSize == 0) {
					setInventorySlotContents(index, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		ItemStack stack = getStackInSlot(index);
		if(stack != null){
			this.setInventorySlotContents(index, stack);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.inventory[index] = stack;
		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
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
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

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
