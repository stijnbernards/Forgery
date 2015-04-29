package com.stijnhero.forgery.client.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.stijnhero.forgery.common.tileentity.TileEntityForgeryFurnace;

public class ContainerForgeryFurnace extends Container {

	public TileEntityForgeryFurnace tileentity;
	private int temperature = 0;

	public ContainerForgeryFurnace(TileEntityForgeryFurnace tileentity, InventoryPlayer inventory_player) {
		this.tileentity = tileentity;
		for (int i = 0; i < tileentity.getSizeInventory(); i++) {
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
		listener.sendProgressBarUpdate(this, 0, (int)this.tileentity.temperature);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); i++) {
			ICrafting crafting = (ICrafting) this.crafters.get(i);
			if(this.temperature != this.tileentity.temperature){
				crafting.sendProgressBarUpdate(this, 0, this.tileentity.temperature);
			}
		}
		this.temperature = this.tileentity.temperature;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		if(id == 0){
			this.tileentity.temperature = data;
		}
	}
}
