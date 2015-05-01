package com.stijnhero.forgery.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

import com.stijnhero.forgery.common.block.BlockHeater;

public class TileEntityHeater extends TileEntity implements IUpdatePlayerListBox {

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
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setDouble("CoalAmount", CoalAmount);
		compound.setDouble("Heat", Heat);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.CoalAmount = compound.getDouble("CoalAmount");
		this.Heat = compound.getDouble("Heat");
	}

	public void SetInventoryStack() {
		CoalAmount += Multiplier;
	}

	public void update() {
		if (CoalAmount > 0) {
			CoalAmount--;
			IsBurning = true;

			if (Heat < MaxHeat)
				Heat++;
		} else {
			IsBurning = false;

			if (Heat > 0)
				Heat--;
		}

		BlockHeater.setState(IsBurning, this.worldObj, this.pos);
	}

	public boolean isBurning() {
		return this.IsBurning;
	}

	public int getCoal() {
		return (int) Math.floor(CoalAmount / Multiplier);
	}

	public double getHeat() {
		return Heat;
	}

	public double getMaxHeat() {
		return MaxHeat;
	}
}
