package com.stijnhero.forgery.common.tileentity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.stijnhero.forgery.recipes.ForgeryFurnaceRecipe;

public class TileEntityForgeryFurnace extends TileEntityForgery implements IFluidHandler, IUpdatePlayerListBox {

	public static final int MAX_TANK = 10000;
	public Map<Fluid, FluidTank> tanks;

	private TileEntityHeater heater = null;

	private double heat = 0.0;
	private double max_heat = 0.0;
	public int temperature = 0;
	public int[] durations = new int[9];

	public TileEntityForgeryFurnace() {
		this.inventory = new ItemStack[9];
		this.tanks = new HashMap<Fluid, FluidTank>();

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
					if (this.heat >= recipe.heat && this.getCurrentFluid() + recipe.amount <= MAX_TANK) {
						if (this.durations[i] >= recipe.duration) {
							this.addFluid(new FluidStack(recipe.fluid, recipe.amount));
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
	}

	private void addFluid(FluidStack fluid) {
		if (!this.tanks.containsKey(fluid.getFluid())) {
			this.tanks.put(fluid.getFluid(), new FluidTank(10000));
		}
		this.tanks.get(fluid.getFluid()).fill(fluid, true);
		worldObj.markBlockForUpdate(this.pos);
	}

	private int getCurrentFluid() {
		int total = 0;
		for (FluidTank tank : this.tanks.values()) {
			if (tank != null) {
				total += tank.getFluidAmount();
			}
		}
		return total;
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
		return 0;// this.tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		// if (resource == null || !resource.isFluidEqual(this.tank.getFluid()))
		// {
		// return null;
		// }
		return null;// this.tank.drain(resource.amount, doDrain);
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
		return null;// new FluidTankInfo[] { this.tank.getInfo() };
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		return null;// this.tank.drain(maxDrain, doDrain);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList list = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		this.inventory = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tcompound = (NBTTagCompound) list.get(i);
			byte b = tcompound.getByte("Slot");
			if (b >= 0 && b < this.getSizeInventory()) {
				this.inventory[b] = ItemStack.loadItemStackFromNBT(tcompound);
			}
		}
		this.durations = compound.getIntArray("Durations");
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.getSizeInventory(); i++) {
			if (this.inventory[i] != null) {
				NBTTagCompound ncompound = new NBTTagCompound();
				ncompound.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(ncompound);
				list.appendTag(ncompound);
			}
		}
		compound.setTag("Items", list);
		compound.setTag("Durations", new NBTTagIntArray(this.durations));
	}

	@SideOnly(Side.CLIENT)
	public int getCraftingProgressScaled(int index, int size) {
		ForgeryFurnaceRecipe recipe = ForgeryFurnaceRecipe.getRecipe(this.inventory[index]);
		int max = 0;
		if (this.durations[index] == 0)
			return 0;
		if (recipe != null) {
			max = recipe.duration;
		}
		if (max == 0) {
			return 0;
		}
		return this.durations[index] * size / max;
	}

	@Override
	protected void readSyncableDataFromNBT(NBTTagCompound compound) {
		NBTTagList fluids = compound.getTagList("Fluids", 10);
		this.tanks.clear();
		for (int iter = 0; iter < fluids.tagCount(); iter++) {
			NBTTagCompound nbt = (NBTTagCompound) fluids.getCompoundTagAt(iter);
			FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt);
			FluidTank tank = new FluidTank(MAX_TANK);
			tank.fill(fluid, true);
			this.tanks.put(fluid.getFluid(), tank);
		}
		super.readSyncableDataFromNBT(compound);
	}

	@Override
	protected void writeSyncableDataToNBT(NBTTagCompound syncData) {
		NBTTagList taglist = new NBTTagList();
		for (FluidTank tank : this.tanks.values()) {
			NBTTagCompound nbt = new NBTTagCompound();
			tank.writeToNBT(nbt);
			taglist.appendTag(nbt);
		}
		syncData.setTag("Fluids", taglist);
		super.writeSyncableDataToNBT(syncData);
	}
}
