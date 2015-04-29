package com.stijnhero.forgery.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.stijnhero.forgery.recipes.ForgeryFurnaceRecipe;

public class TileEntityForgeryFurnace extends TileEntityForgery implements IFluidHandler, IUpdatePlayerListBox {

	private FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME);

	private TileEntityHeater heater = null;

	private double heat = 0.0;
	private double max_heat = 0.0;
	public int temperature = 0;
	public int[] durations = new int[9];

	public TileEntityForgeryFurnace() {
		this.inventory = new ItemStack[9];
		this.tank.setCapacity(10000);

		for (int i = 0; i < 9; i++) {
			this.durations[i] = 0;
		}
	}

	@Override
	public String getName() {
		return "forgery.furnace";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	public void setHeaterTileEntity(TileEntityHeater heater) {
		this.heater = heater;
	}

	boolean f = true;

	@Override
	public void update() {
		if (!this.worldObj.isRemote) {
			if (this.heater == null && f) {
				f = false;
				loadHeater(this.worldObj, this.pos);
			}
			if (this.heater != null) {
				this.heat = this.heater.getHeat();
				this.max_heat = this.heater.getMaxHeat();
			} else {
				this.heat = 0;
				this.max_heat = 0;
				this.temperature = 0;
			}

			if (this.heat > this.max_heat) {
				this.heat = this.max_heat;
			}
		}

		if (this.heater != null) {
			this.temperature = (int) (this.heat / (this.max_heat / 100));
			this.handleCrafting();
		}
	}

	private void handleCrafting() {
		for (int i = 0; i < this.inventory.length; i++) {
			if (this.inventory[i] != null && this.inventory[i].stackSize > 0) {
				ForgeryFurnaceRecipe recipe = ForgeryFurnaceRecipe.getRecipe(this.inventory[i]);
				if (recipe != null) {
					if (this.heat >= recipe.heat && this.tank.getFluidAmount() + recipe.amount <= this.tank.getCapacity()) {
						if (this.durations[i] >= recipe.duration) {
							this.tank.fill(new FluidStack(recipe.fluid, recipe.amount), true);
							this.inventory[i].stackSize--;
							this.durations[i] = 0;
							if (this.inventory[i].stackSize <= 0) {
								this.inventory[i] = null;
							}
						} else {
							this.durations[i]++;
						}
					}
					continue;
				}
			}
			this.durations[i] = 0;
		}
		System.out.println(this.tank.getFluidAmount());
	}

	public static void loadHeater(World world, BlockPos pos) {
		TileEntityForgeryFurnace tileentity = (TileEntityForgeryFurnace) world.getTileEntity(pos);
		TileEntity te = world.getTileEntity(pos.down(1));
		if (te instanceof TileEntityHeater) {
			if (tileentity != null) {
				tileentity.setHeaterTileEntity((TileEntityHeater) te);
			}
		} else {
			if (tileentity != null) {
				tileentity.setHeaterTileEntity(null);
			}
		}
	}

	public double getHeat() {
		return this.heat;
	}

	public double getMaxHeat() {
		return this.max_heat;
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		return this.tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		if (resource == null || !resource.isFluidEqual(this.tank.getFluid())) {
			return null;
		}
		return this.tank.drain(resource.amount, doDrain);
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		return new FluidTankInfo[] { this.tank.getInfo() };
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		return this.tank.drain(maxDrain, doDrain);
	}
}
