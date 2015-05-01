package com.stijnhero.forgery.client.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.stijnhero.forgery.common.tileentity.TileEntityForgeryFurnace;

public class ContainerForgeryFurnace extends Container {

	public TileEntityForgeryFurnace tileentity;
	private int temperature = 0;
	private int durations[] = new int[9];

	public ContainerForgeryFurnace(TileEntityForgeryFurnace tileentity, InventoryPlayer inventory_player) {
		this.tileentity = tileentity;
		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(this.tileentity, i, 8 + 18 * i, 8));
		}

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(inventory_player, x + y * 9 + 9, 8 + 18 * x, 113 + y * 18));
			}
		}

		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(inventory_player, x, 8 + 18 * x, 171));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileentity.isUseableByPlayer(player);
	}

	@Override
	public void addCraftingToCrafters(ICrafting listener) {
		super.addCraftingToCrafters(listener);
		listener.sendProgressBarUpdate(this, 0, (int) this.tileentity.temperature);
		for (int i = 0; i < 9; i++) {
			listener.sendProgressBarUpdate(this, i + 1, this.tileentity.durations[i]);
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); i++) {
			ICrafting crafting = (ICrafting) this.crafters.get(i);
			if (this.temperature != this.tileentity.temperature) {
				crafting.sendProgressBarUpdate(this, 0, this.tileentity.temperature);
			}
			for (int j = 0; j < 9; j++) {
				crafting.sendProgressBarUpdate(this, j + 1, this.tileentity.durations[j]);
			}
		}
		this.temperature = this.tileentity.temperature;
		for (int i = 0; i < 9; i++) {
			this.durations[i] = this.tileentity.durations[i];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		if (id == 0) {
			this.tileentity.temperature = data;
		}
		if (id >= 1 && id <= 9) {
			this.tileentity.durations[id - 1] = data;
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			if (slot < tileentity.getSizeInventory()) {
				if (!this.mergeItemStack(stackInSlot, tileentity.getSizeInventory(), 36 + tileentity.getSizeInventory(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(stackInSlot, 0, tileentity.getSizeInventory(), false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}
}
