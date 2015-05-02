package com.stijnhero.forgery.common.tileentity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.stijnhero.forgery.ForgeryFluids;
import com.stijnhero.forgery.common.block.BlockChimney;
import com.stijnhero.forgery.common.block.BlockForgeryFurnace;
import com.stijnhero.forgery.common.tileentity.heater.TileEntityHeater;
import com.stijnhero.forgery.recipes.ForgeryFurnaceRecipe;

public class TileEntityForgeryFurnace extends TileEntityForgery implements IFluidHandler, IUpdatePlayerListBox {

	public static final int MAX_TANK = 10000;
	public Map<Fluid, FluidTank> tanks;
	private FluidTank slag;

	private TileEntityHeater heater = null;

	private double heat = 0.0;
	private double max_heat = 0.0;
	public int temperature = 0;
	public int[] durations = new int[9];
	private boolean busy = false;
	private int chimney_height = 0;

	public TileEntityForgeryFurnace() {
		this.inventory = new ItemStack[9];
		this.tanks = new HashMap<Fluid, FluidTank>();
		this.slag = new FluidTank(1000);

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

	int ticks = 0;
	int dticks = 0;

	@Override
	public void update() {
		if (ticks == 12) {
			this.outputFluids();
			ticks = 0;
		}
		ticks++;
		loadHeater(this.worldObj, this.pos);

		if (this.heater != null) {
			this.heat = this.heater.getHeat();
			this.max_heat = this.heater.getMaxHeat();
		} else {
			this.heat = 0;
			this.max_heat = 0;
			this.temperature = 0;
			this.busy = false;
		}

		if (this.heat > this.max_heat) {
			this.heat = this.max_heat;
		}

		if (this.heater != null) {
			if (!this.worldObj.isRemote) {
				this.temperature = (int) (this.heat / (this.max_heat / 100));
			}
			this.handleCrafting();
		}
	}

	int ch = 0;

	private void handleCrafting() {
		for (int i = 0; i < this.inventory.length; i++) {
			if (this.inventory[i] != null && this.inventory[i].stackSize > 0) {
				ForgeryFurnaceRecipe recipe = ForgeryFurnaceRecipe.getRecipe(this.inventory[i]);
				if (recipe != null) {
					if (this.heat >= recipe.heat && this.getCurrentFluid() + recipe.amount <= MAX_TANK && this.slag.getFluidAmount() + recipe.slag <= this.slag.getCapacity()) {
						if (this.durations[i] >= recipe.duration) {
							this.addFluid(new FluidStack(recipe.fluid, recipe.amount));
							this.inventory[i].stackSize--;
							this.durations[i] = 0;
							this.slag.fill(new FluidStack(ForgeryFluids.Slag, recipe.slag), true);
							if (this.inventory[i].stackSize <= 0) {
								this.inventory[i] = null;
							}
						} else {
							this.durations[i]++;
							this.busy = true;
							if (ch == 1) {
								this.chimney();
								ch = 0;
							}
							ch++;

							if (dticks == 16) {
								dticks = 0;
								if (chimney_height <= 5) {
									EntityPlayer player = this.worldObj.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5);
									if (player != null) {
										if (!player.capabilities.isCreativeMode) {
											if (!player.isPotionActive(Potion.blindness)) {
												player.addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 2));
												player.addPotionEffect(new PotionEffect(Potion.weakness.id, 100, 2));
												player.addPotionEffect(new PotionEffect(Potion.poison.id, 100, 2));
												player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 2));
											}
										}
									}
								}
							}
							dticks++;
						}
					}
					continue;
				}
			}
			this.durations[i] = 0;
			this.busy = false;
		}
	}

	private void addFluid(FluidStack fluid) {
		if (!this.tanks.containsKey(fluid.getFluid())) {
			this.tanks.put(fluid.getFluid(), new FluidTank(10000));
		}
		this.tanks.get(fluid.getFluid()).fill(fluid, true);
		worldObj.markBlockForUpdate(this.pos);
	}

	public int getCurrentFluid() {
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
		return 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		if (resource == null)
			return null;
		if (this.tanks.containsKey(resource.getFluid())) {
			return this.tanks.get(resource.getFluid()).drain(resource.amount, doDrain);
		}
		return null;
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
		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		return null;
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

		NBTTagList fluids = compound.getTagList("Fluids", Constants.NBT.TAG_COMPOUND);
		this.tanks.clear();
		for (int iter = 0; iter < fluids.tagCount(); iter++) {
			NBTTagCompound nbt = (NBTTagCompound) fluids.getCompoundTagAt(iter);
			FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt);
			if (fluid == null)
				continue;
			FluidTank tank = new FluidTank(MAX_TANK);
			tank.fill(fluid, true);
			this.tanks.put(fluid.getFluid(), tank);
		}

		NBTTagCompound slagc = compound.getCompoundTag("Slag");
		this.slag.readFromNBT(slagc);
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

		NBTTagList taglist = new NBTTagList();
		for (FluidTank tank : this.tanks.values()) {
			NBTTagCompound nbt = new NBTTagCompound();
			tank.writeToNBT(nbt);
			taglist.appendTag(nbt);
		}
		compound.setTag("Fluids", taglist);

		NBTTagCompound slagc = new NBTTagCompound();
		slag.writeToNBT(slagc);
		compound.setTag("Slag", slagc);
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
		NBTTagList fluids = compound.getTagList("Fluids", Constants.NBT.TAG_COMPOUND);
		this.tanks.clear();
		for (int iter = 0; iter < fluids.tagCount(); iter++) {
			NBTTagCompound nbt = (NBTTagCompound) fluids.getCompoundTagAt(iter);
			FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt);
			FluidTank tank = new FluidTank(MAX_TANK);
			tank.fill(fluid, true);
			this.tanks.put(fluid.getFluid(), tank);
		}
		this.slag.readFromNBT(compound.getCompoundTag("Slag"));
		super.readSyncableDataFromNBT(compound);
	}

	@Override
	protected void writeSyncableDataToNBT(NBTTagCompound syncData) {
		NBTTagList taglist = new NBTTagList();
		for (FluidTank tank : this.tanks.values()) {
			if (tank.getFluid() == null)
				continue;
			NBTTagCompound nbt = new NBTTagCompound();
			tank.getFluid().writeToNBT(nbt);
			taglist.appendTag(nbt);
		}
		syncData.setTag("Fluids", taglist);
		NBTTagCompound slagc = new NBTTagCompound();
		slag.writeToNBT(slagc);
		syncData.setTag("Slag", slagc);
		super.writeSyncableDataToNBT(syncData);
	}

	public boolean isBusy() {
		return this.busy;
	}

	public FluidTank getSlag() {
		return this.slag;
	}

	Random rand = new Random();
	boolean chimney = false;
	boolean gap = false;

	private void chimney() {
		chimney_height = 0;
		chimney = false;
		for (int i = 0; i < 10; i++) {
			Block block = worldObj.getBlockState(pos.up(i + 1)).getBlock();
			if (block instanceof BlockChimney) {
				if (!gap) {
					chimney = true;
					gap = true;
				}
			} else {
				gap = true;
				chimney = false;
			}

			if (chimney) {
				chimney_height++;
			}
			if (!chimney) {
				double n = ((1.0 / 20) * (i + 5));
				double f = (1.0 - ((1.0 / 20) * (i + 5)));

				for (int j = 0; j < 16; j += 2) {
					double h = j;
					if (j > 0) {
						h = 1.0 / (j - 6);
					}
					worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + n, pos.getY() + 1 + i + h + 0.5, pos.getZ() + f - (0.45 - rand.nextDouble() / 2), 0.0D, 0.0D, 0.0D, new int[0]);
					worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + f - (0.45 - rand.nextDouble() / 2.0), pos.getY() + 1 + i + h + 0.5, pos.getZ() + f, 0.0D, 0.0D, 0.0D, new int[0]);
					worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + n + (0.45 - rand.nextDouble() / 2), pos.getY() + 1 + i + h + 0.5, pos.getZ() + n, 0.0D, 0.0D, 0.0D, new int[0]);
					worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + f, pos.getY() + 1 + i + h + 0.5, pos.getZ() + n + (0.45 - rand.nextDouble() / 2), 0.0D, 0.0D, 0.0D, new int[0]);
				}
			}
		}
		gap = false;
	}

	private void outputFluids() {
		BlockForgeryFurnace block = (BlockForgeryFurnace) this.worldObj.getBlockState(pos).getBlock();
		IBlockState state = this.worldObj.getBlockState(pos);
		EnumFacing enumfacing = (EnumFacing) state.getValue(block.FACING);

		// Output slag
		EnumFacing slagside = enumfacing.rotateY();
		TileEntity tes = this.worldObj.getTileEntity(this.pos.offset(slagside));
		if (tes != null && tes instanceof IFluidHandler) {
			if (tes.getWorld() != null) {
				IFluidHandler fe = (IFluidHandler) tes;
				if (slag != null && slag.getFluidAmount() > 0) {
					this.emptyTank(fe, slagside.getOpposite(), slag.getFluid().copy(), slag, 100);
				}
			}
		}

		// Output fluids
		EnumFacing fluids = enumfacing.rotateY().rotateY().rotateY();
		TileEntity te = this.worldObj.getTileEntity(this.pos.offset(fluids));
		if (te != null && te instanceof IFluidHandler) {
			if (te.getWorld() != null) {
				IFluidHandler f = (IFluidHandler) te;
				for (FluidTank tank : this.tanks.values()) {
					if (tank != null && tank.getFluidAmount() > 0) {
						this.emptyTank(f, fluids.getOpposite(), tank.getFluid().copy(), tank, 100);
					}
				}
			}
		}
	}

	private void emptyTank(IFluidHandler target, EnumFacing facing, FluidStack fluid, FluidTank tank, int drain) {
		if (target.canFill(facing, fluid.getFluid())) {
			if (target.getTankInfo(facing) == null)
				return;
			if (target.getTankInfo(facing).length <= 0)
				return;
			if (target.getTankInfo(facing)[0].fluid == null) {
				fluid.amount = drain;
				if (target.fill(facing, fluid, true) > 0) {
					tank.drain(drain, true);
					this.worldObj.markBlockForUpdate(((TileEntity) target).getPos());
					this.worldObj.markBlockForUpdate(pos);
				}
			} else {
				if (target.getTankInfo(facing)[0].fluid.isFluidEqual(fluid)) {
					fluid.amount = drain;
					if (target.fill(facing, fluid, true) > 0) {
						tank.drain(drain, true);
						this.worldObj.markBlockForUpdate(((TileEntity) target).getPos());
						this.worldObj.markBlockForUpdate(pos);
					}
				}
			}
		}
	}
}
