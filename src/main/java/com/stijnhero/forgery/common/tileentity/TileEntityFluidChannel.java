package com.stijnhero.forgery.common.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.stijnhero.forgery.Forgery;
import com.stijnhero.forgery.ForgeryFluids;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.common.util.EnumHelper;

public class TileEntityFluidChannel extends TileEntity implements IFluidHandler, IUpdatePlayerListBox {

	FluidTank internalTank = new FluidTank(1000);
	HashMap<EnumFacing, FluidTank> subTanks = new HashMap();
	public EnumFacing lastProvider;
	public ArrayList<EnumFacing> validOutputs = new ArrayList();
	public int ticks = 0;
	public int recentlyFilledDelay;

	public TileEntityFluidChannel() {
	}

	public TileEntityFluidChannel(boolean test) {
		lastProvider = null;
		validOutputs.add(EnumFacing.DOWN);
		validOutputs.add(EnumFacing.NORTH);
		validOutputs.add(EnumFacing.SOUTH);
		validOutputs.add(EnumFacing.EAST);
		validOutputs.add(EnumFacing.WEST);

		subTanks.put(EnumFacing.NORTH, new FluidTank(1000));
		subTanks.put(EnumFacing.SOUTH, new FluidTank(1000));
		subTanks.put(EnumFacing.WEST, new FluidTank(1000));
		subTanks.put(EnumFacing.EAST, new FluidTank(1000));

		if (test)
			this.internalTank.fill(new FluidStack(ForgeryFluids.LiquidCopper, 1), true);
	}

	public void changeOutputs(EntityPlayer player, int i, float hitX, float hitY, float hitZ) {
		EnumFacing toggle = null;
		if (i == 0 || i == 1) {
			if (hitX > 0.3125 && hitX < 0.6875)
				if (hitZ > 0 && hitZ < 0.3125)
					toggle = EnumFacing.NORTH;
			if (hitX > 0.3125 && hitX < 0.6875)
				if (hitZ > 0.6875 && hitZ < 1)
					toggle = EnumFacing.SOUTH;
			if (hitZ > 0.3125 && hitZ < 0.6875)
				if (hitX > 0 && hitX < 0.3125)
					toggle = EnumFacing.WEST;
			if (hitZ > 0.3125 && hitZ < 0.6875)
				if (hitX > 0.6875 && hitX < 1)
					toggle = EnumFacing.EAST;

			if (i == 0)
				if (hitX > 0.3125 && hitX < 0.6875)
					if (hitZ > 0.3125 && hitZ < 0.6875)
						toggle = EnumFacing.DOWN;
		} else {
			if (i == 2) {
				if (hitX > 0 && hitX < 0.3125)
					toggle = EnumFacing.WEST;
				if (hitX > 0.6875 && hitX < 1)
					toggle = EnumFacing.EAST;
			}
			if (i == 3) {
				if (hitX > 0 && hitX < 0.3125)
					toggle = EnumFacing.WEST;
				if (hitX > 0.6875 && hitX < 1)
					toggle = EnumFacing.EAST;
			}
			if (i == 4) {
				if (hitZ > 0 && hitZ < 0.3125)
					toggle = EnumFacing.NORTH;
				if (hitZ > 0.6875 && hitZ < 1)
					toggle = EnumFacing.SOUTH;
			}
			if (i == 5) {
				if (hitZ > 0 && hitZ < 0.3125)
					toggle = EnumFacing.NORTH;
				if (hitZ > 0.6875 && hitZ < 1)
					toggle = EnumFacing.SOUTH;
			}
		}

		if (toggle != null && toggle != EnumFacing.UP) {
			TileEntity tile = worldObj.getTileEntity(pos.add(toggle.getDirectionVec()));
			if (tile instanceof IFluidHandler)
				if (validOutputs.contains(toggle)) {
					validOutputs.remove(toggle);
					if (tile instanceof TileEntityFluidChannel && toggle != EnumFacing.DOWN)
						((TileEntityFluidChannel) tile).validOutputs.remove(toggle.getOpposite());
				} else {
					validOutputs.add(toggle);
					if (tile instanceof TileEntityFluidChannel && toggle != EnumFacing.DOWN)
						if (!((TileEntityFluidChannel) tile).validOutputs.contains(toggle.getOpposite()))
							((TileEntityFluidChannel) tile).validOutputs.add(toggle.getOpposite());
				}

		}
		markDirtyForRendering();
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		if (resource == null)
			return 0;
		if (doFill) {
			markDirtyForRendering();
			this.lastProvider = from;
		}

		if (from == EnumFacing.UP) {
			return this.internalTank.fill(resource, doFill);
		} else if (from == EnumFacing.NORTH || from == EnumFacing.SOUTH || from == EnumFacing.WEST || from == EnumFacing.EAST) {
			if (this.subTanks.get(from) != null) {
				return this.subTanks.get(from).fill(resource, doFill);
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {

		FluidStack fluidStack = null;
		if (from == EnumFacing.DOWN)
			if (this.internalTank.getFluid() != null)
				fluidStack = this.internalTank.getFluid();
			else if (validOutputs.contains(from))
				fluidStack = this.subTanks.get(from).getFluid();

		// wrong fluid
		if (fluidStack != null && fluidStack.getFluid() != resource.getFluid())
			return null;

		return drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		if (from == EnumFacing.DOWN)
			return this.internalTank.drain(maxDrain, doDrain);
		else if (from == EnumFacing.NORTH || from == EnumFacing.SOUTH || from == EnumFacing.WEST || from == EnumFacing.EAST) {
			return this.subTanks.get(from).drain(maxDrain, doDrain);
		}

		return null;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		if (from == EnumFacing.DOWN)
			return false;
		if (from == EnumFacing.UP)
			return true;

		return validOutputs.contains(from);
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		if (from == EnumFacing.UP)
			return false;

		return validOutputs.contains(from);
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		if (from == null || from == EnumFacing.UP || from == EnumFacing.DOWN)
			return new FluidTankInfo[] { new FluidTankInfo(internalTank) };
		else
			return new FluidTankInfo[] { new FluidTankInfo(subTanks.get(from)) };
	}

	public void readCustomNBT(NBTTagCompound tags) {

		NBTTagCompound nbtTank = tags.getCompoundTag("internalTank");
		if (nbtTank != null)
			internalTank.readFromNBT(nbtTank);

		for (EnumFacing efSub : subTanks.keySet()) {
			NBTTagCompound nbtSubTank = tags.getCompoundTag("subTank_" + efSub.name());
			if (nbtSubTank != null)
				subTanks.get(efSub).readFromNBT(nbtSubTank);
		}

		int[] validEFs = tags.getIntArray("validOutputs");
		if (validEFs != null) {
			validOutputs = new ArrayList();
			for (int i : validEFs)
				validOutputs.add(convertIntToEF(i));
		}

		this.lastProvider = this.convertIntToEF(tags.getInteger("LastProvider"));
	}

	@Override
	public void writeToNBT(NBTTagCompound tags) {
		super.writeToNBT(tags);
		writeCustomNBT(tags);
	}

	public void writeCustomNBT(NBTTagCompound tags) {
		NBTTagCompound nbtTank = new NBTTagCompound();
		internalTank.writeToNBT(nbtTank);
		tags.setTag("internalTank", nbtTank);
		for (EnumFacing efSub : subTanks.keySet()) {
			NBTTagCompound nbtSubTank = new NBTTagCompound();
			subTanks.get(efSub).writeToNBT(nbtSubTank);
			tags.setTag("subTank_" + efSub.name(), nbtSubTank);
		}

		int[] validEFs = new int[validOutputs.size()];
		int it = 0;
		for (EnumFacing ef : validOutputs) {
			if (ef != null)
				validEFs[it] = convertEFToInt(ef);
			it++;
		}
		tags.setIntArray("validOutputs", validEFs);
		tags.setInteger("LastProvider", this.convertEFToInt(this.lastProvider));
	}

	@Override
	public void update() {
		ticks++;

		if (ticks == 6)
			this.distributeFluids();
		if (ticks >= 12) {
			if (recentlyFilledDelay != 0)
				recentlyFilledDelay--;
			if (recentlyFilledDelay == 0 || lastProvider == EnumFacing.UP)
				this.outputFluids();

			ticks = 0;
		}
	}

	private void outputFluids() {
		HashMap<EnumFacing, TileEntity> connected = this.getOutputs();
		if (connected.containsKey(lastProvider))
			connected.remove(lastProvider);
		if (connected.containsKey(EnumFacing.DOWN) && this.internalTank.getFluid() != null) {
			int output = Math.min(internalTank.getFluid().amount, 100);
			FluidStack tempFS = new FluidStack(internalTank.getFluid().getFluid(), output);
			int fittingBelow = ((IFluidHandler) connected.get(EnumFacing.DOWN)).fill(EnumFacing.UP, tempFS, false);
			if (fittingBelow > 0)
				fittingBelow = this.drain(EnumFacing.DOWN, fittingBelow, true).amount;
			tempFS.amount = fittingBelow;
			fittingBelow = ((IFluidHandler) connected.get(EnumFacing.DOWN)).fill(EnumFacing.UP, tempFS, true);

			connected.remove(EnumFacing.DOWN);
		}
		if (connected.size() != 0) {
			for (EnumFacing dir : connected.keySet()) {
				if (subTanks.get(dir) != null && subTanks.get(dir).getFluid() != null) {
					FluidStack tempFS = new FluidStack(subTanks.get(dir).getFluid().getFluid(), Math.min(subTanks.get(dir).getFluidAmount(), 100));
					int fit = ((IFluidHandler) connected.get(dir)).fill(dir.getOpposite(), tempFS, false);

					if (fit > 0)
						fit = this.drain(dir, fit, true).amount;

					tempFS.amount = fit;
					fit = ((IFluidHandler) connected.get(dir)).fill(dir.getOpposite(), tempFS, true);
				}
			}
		}
	}

	public HashMap<EnumFacing, TileEntity> getOutputs() {
		HashMap<EnumFacing, TileEntity> map = new HashMap();
		for (EnumFacing ef : this.validOutputs) {
			TileEntity tile = this.worldObj.getTileEntity(pos.add(ef.getDirectionVec()));

			if (tile != null && tile instanceof IFluidHandler)
				map.put(ef, tile);
		}
		return map;
	}

	private void distributeFluids() {

		// Move Fluid to Output Tanks
		Set<EnumFacing> connected = this.getOutputs().keySet();

		if (connected.contains(EnumFacing.DOWN))
			connected.remove(EnumFacing.DOWN);
		if (connected.contains(lastProvider))
			connected.remove(lastProvider);

		int output = Math.min(internalTank.getFluidAmount(), 100);
		int connectedAmount = connected.size();
		if (connectedAmount < 1)
			connectedAmount = 1;
		int scaledAmount = output / connectedAmount;

		for (EnumFacing dirOut : connected) {
			if (this.internalTank.getFluid() == null)
				break;

			FluidStack tempFS = new FluidStack(internalTank.getFluid().getFluid(), scaledAmount);
			int fit = subTanks.get(dirOut).fill(tempFS, false);

			if (fit > 0)
				fit = internalTank.drain(fit, true).amount;

			tempFS.amount = fit;
			fit = subTanks.get(dirOut).fill(tempFS, true);

			markDirtyForRendering();
		}

		// Get Fluid from most recent InputTank
		FluidTank inputTank = subTanks.get(lastProvider);
		if (inputTank != null && inputTank.getFluid() != null) // Tank can be
																// null if input
																// was received
																// from top
		{

			FluidStack tempFS = new FluidStack(inputTank.getFluid().getFluid(), Math.min(inputTank.getFluidAmount(), 100));
			int fit = internalTank.fill(tempFS, false);

			if (fit > 0)
				fit = inputTank.drain(fit, true).amount;

			tempFS.amount = fit;
			fit = internalTank.fill(tempFS, true);

			markDirtyForRendering();
		}
	}

	private void markDirtyForRendering() {
		this.worldObj.markBlockForUpdate(pos);
	}

	public int convertEFToInt(EnumFacing dir) {
		if (dir == null)
			return -1;
		switch (dir) {
		case DOWN:
			return 0;
		case UP:
			return 1;
		case NORTH:
			return 2;
		case SOUTH:
			return 3;
		case WEST:
			return 4;
		case EAST:
			return 5;
		default:
			return -1;
		}
	}

	public EnumFacing convertIntToEF(int i) {
		switch (i) {
		case 0:
			return EnumFacing.DOWN;
		case 1:
			return EnumFacing.UP;
		case 2:
			return EnumFacing.NORTH;
		case 3:
			return EnumFacing.SOUTH;
		case 4:
			return EnumFacing.WEST;
		case 5:
			return EnumFacing.EAST;
		default:
			return null;
		}
	}

}
