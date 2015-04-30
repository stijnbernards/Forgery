package com.stijnhero.forgery.common.block;

import com.stijnhero.forgery.Forgery;
import com.stijnhero.forgery.ForgeryBlocks;
import com.stijnhero.forgery.common.tileentity.TileEntityFluidChannel;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFluidChannel extends BlockContainer {

	private boolean test = true;

	public BlockFluidChannel(boolean kek) {
		super(Material.rock);
		this.setHardness(1F);
		this.setResistance(10);
		this.stepSound = soundTypeStone;
		setCreativeTab(Forgery.Forgery);
		this.test = kek;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getCurrentEquippedItem();
		TileEntityFluidChannel tile = (TileEntityFluidChannel) worldIn.getTileEntity(pos);

		if (tile.getTankInfo(EnumFacing.UP)[0].fluid != null) {
			System.out.println("FLUID: " + tile.getTankInfo(EnumFacing.UP)[0].fluid.getLocalizedName() + ", AMOUNT: " + tile.getTankInfo(EnumFacing.UP)[0].fluid.amount);
		}
		
		if (stack != null && stack.getItem() == Item.getItemFromBlock(ForgeryBlocks.FluidChannel))
			return false;
		else {
			tile.changeOutputs(playerIn, side.getIndex(), hitX, hitY, hitZ);
			return true;
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
		TileEntityFluidChannel tile = (TileEntityFluidChannel) world.getTileEntity(pos);
		float minX = 0.3125F;
		float maxX = 0.6875F;
		float minZ = 0.3125F;
		float maxZ = 0.6875F;
		minZ = 0F;
		maxZ = 1F;
		minX = 0F;
		maxX = 1F;
		this.setBlockBounds(minX, 0.375F, minZ, maxX, 0.625F, maxZ);
	}

	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	// @Override
	// public int getRenderType ()
	// {
	// return BlockRenderCastingChannel.renderID;
	// }

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityFluidChannel(test);
	}
}