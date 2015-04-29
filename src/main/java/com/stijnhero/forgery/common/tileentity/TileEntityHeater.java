package com.stijnhero.forgery.common.tileentity;

import java.util.Random;

import com.stijnhero.forgery.common.block.BlockHeater;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;

public class TileEntityHeater extends TileEntity implements IUpdatePlayerListBox{

	double CoalAmount = 0.0;
	boolean IsBurning;
	double Multiplier;
	double MaxHeat = 0.0;
	double Heat = 0.0;
	
	
	public TileEntityHeater(double multi, double max) {
		this.Multiplier = multi;
		this.MaxHeat = max;
	}
	
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
		CoalAmount += Multiplier;
	}
	
	public void update(){
		if(CoalAmount > 0){
			CoalAmount--;
			IsBurning = true;
			
			if(Heat < MaxHeat)
				Heat++;
		}else{
			IsBurning = false;
			
			if(Heat > 0)
				Heat--;
		}
		
		BlockHeater.setState(IsBurning, this.worldObj, this.pos);
	}
	
	public boolean isBurning(){
		return this.IsBurning;
	}
	
	public int getCoal(){
		return (int) Math.floor(CoalAmount / Multiplier);
	}
	
	public double getHeat(){
		return Heat;
	}
	
	public double getMaxHeat(){
		return MaxHeat;
	}
	
	public int getRenderType()
    {
        return 3;
    }
}
