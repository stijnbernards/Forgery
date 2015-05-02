package com.stijnhero.forgery.common.block;

import java.util.List;

import com.stijnhero.forgery.Forgery;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockChimney extends Block {

	public BlockChimney(Material materialIn) {
		super(materialIn);
		this.setCreativeTab(Forgery.Forgery);
	}

	@Override
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {
		this.setBlockBounds(0.312F, 0.0F, 0.312F, 0.6875F, 1.0F, 0.6875F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		return new AxisAlignedBB(pos.getX() + 0.312F, pos.getY() + 0.0F, pos.getZ() + 0.312F, pos.getX() + 0.6875F, pos.getY() + 1.0F, pos.getZ() + 0.6875F);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		this.setBlockBounds(0.312F, 0.0F, 0.312F, 0.6875F, 1.0F, 0.6875F);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean isFullCube() {
		return false;
	}

	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}
}
