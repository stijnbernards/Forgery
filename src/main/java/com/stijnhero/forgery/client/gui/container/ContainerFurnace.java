package com.stijnhero.forgery.client.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import com.stijnhero.forgery.common.tileentity.TileEntityFurnace;

public class ContainerFurnace extends Container {

	public TileEntityFurnace tileentity;
	
	public ContainerFurnace(TileEntityFurnace tileentity, InventoryPlayer inventory_player){
		this.tileentity = tileentity;
		for(int i = 0; i < tileentity.getSizeInventory(); i++){
			this.addSlotToContainer(new Slot(this.tileentity, i, 100, 100 + 30 * i));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileentity.isUseableByPlayer(player);
	}
}
