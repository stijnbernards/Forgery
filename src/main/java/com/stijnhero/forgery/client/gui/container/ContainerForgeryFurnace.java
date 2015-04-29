package com.stijnhero.forgery.client.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import com.stijnhero.forgery.common.tileentity.TileEntityForgeryFurnace;

public class ContainerForgeryFurnace extends Container {

	public TileEntityForgeryFurnace tileentity;
	
	public ContainerForgeryFurnace(TileEntityForgeryFurnace tileentity, InventoryPlayer inventory_player){
		this.tileentity = tileentity;
		for(int i = 0; i < tileentity.getSizeInventory(); i++){
			this.addSlotToContainer(new Slot(this.tileentity, i, 8 + 18 * i, 8));
		}

		for (int y = 0; y < 3; y++){
			for (int x = 0; x < 9; x++){
				addSlotToContainer(new Slot(inventory_player, x + y * 9 + 9, 8 + 18 * x, 113 + y * 18));
			}
		}
		
		for (int x = 0; x < 9; x++){
			addSlotToContainer(new Slot(inventory_player, x, 8 + 18 * x, 171));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileentity.isUseableByPlayer(player);
	}
}
