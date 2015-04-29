package com.stijnhero.forgery.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.stijnhero.forgery.Forgery;
import com.stijnhero.forgery.client.gui.Guis;
import com.stijnhero.forgery.common.tileentity.TileEntityForgeryFurnace;
import com.stijnhero.forgery.common.tileentity.TileEntityHeater;

public class BlockForgeryFurnace extends BlockContainer {

	public BlockForgeryFurnace(Material material) {
		super(material);
		this.setCreativeTab(Forgery.Forgery);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityForgeryFurnace();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity tileentity = world.getTileEntity(pos);
		if(tileentity == null || player.isSneaking()){
			return false;
		}
		player.openGui(Forgery.instance, Guis.FORGERY_FURNACE, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbor) {
		TileEntityForgeryFurnace.loadHeater(world, pos);
	}
}
