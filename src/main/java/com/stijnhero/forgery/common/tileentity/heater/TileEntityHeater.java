package com.stijnhero.forgery.common.tileentity.heater;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

import com.stijnhero.forgery.common.block.BlockHeater;

public class TileEntityHeater extends TileEntity implements IUpdatePlayerListBox {

	protected double CoalAmount = 0.0;
	protected boolean IsBurning;
	protected double Multiplier = 0.0;
	protected double MaxHeat = 0.0;
	protected double Heat = 0.0;
	protected Block active_block;
	protected Block normal_block;

	public TileEntityHeater() {
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
	
	public Block getDefaultBlock(){
		return this.normal_block;
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

		BlockHeater.setState(IsBurning, this.worldObj, this.pos, this.normal_block, this.active_block);
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

	public void setMultiplier(double multi) {
		this.Multiplier = multi;
	}

	public void setMaxHeat(double heat) {
		this.MaxHeat = heat;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeSyncableDataToNBT(syncData);
		return new S35PacketUpdateTileEntity(this.pos, 1, syncData);
	}

	protected void writeSyncableDataToNBT(NBTTagCompound compound) {
		compound.setDouble("Multiplier", Multiplier);
		compound.setDouble("CoalAmount", CoalAmount);
		compound.setDouble("Heat", this.Heat);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readSyncableDataFromNBT(pkt.getNbtCompound());
		markDirty();
		worldObj.markBlockForUpdate(pos);
	}

	protected void readSyncableDataFromNBT(NBTTagCompound compound) {
		this.CoalAmount = compound.getDouble("CoalAmount");
		this.Multiplier = compound.getDouble("Multiplier");
		this.Heat = compound.getDouble("Heat");
	}
}
