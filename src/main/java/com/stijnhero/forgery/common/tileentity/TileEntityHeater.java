package com.stijnhero.forgery.common.tileentity;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

public class TileEntityHeater extends TileEntity{

	double CoalAmount = 0.0;
	
	@Override
	public void writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setDouble("CoalAmount", CoalAmount);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.CoalAmount = compound.getDouble("CoalAmount");
	}
	
	public void SetInventoryStack(){
		CoalAmount++;
		
		System.out.println(CoalAmount);
	}
	
	public int getRenderType()
    {
        return 3;
    }
}
