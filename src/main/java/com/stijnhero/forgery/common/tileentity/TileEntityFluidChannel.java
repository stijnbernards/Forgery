package com.stijnhero.forgery.common.tileentity;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.common.util.EnumHelper;

public class TileEntityFluidChannel extends TileEntity implements IFluidHandler, IUpdatePlayerListBox{

	FluidTank internalTank = new FluidTank(1);
    HashMap<EnumFacing, FluidTank> subTanks = new HashMap();
    public EnumFacing lastProvider;
    public ArrayList<EnumFacing> validOutputs = new ArrayList();
    public int ticks = 0;
    public int recentlyFilledDelay;
	
    public TileEntityFluidChannel() {
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource,
			boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
