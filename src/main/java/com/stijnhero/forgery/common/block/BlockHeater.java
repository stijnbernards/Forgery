package com.stijnhero.forgery.common.block;

import com.stijnhero.forgery.Forgery;
import com.stijnhero.forgery.common.tileentity.TileEntityHeater;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockHeater extends BlockContainer{

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public BlockHeater(Material materialIn) {
		super(materialIn);
        //this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(Forgery.Forgery);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityHeater();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getCurrentEquippedItem();
		TileEntityHeater tile = (TileEntityHeater) worldIn.getTileEntity(pos);
		
		if(stack != null && stack.getItem() == Items.coal){
			stack.stackSize--;
			tile.SetInventoryStack();
		}
		
		return true;
	}
	
    public boolean isOpaqueCube(){
    	return false;
    }
    
    public boolean renderAsNormalBlock(){
    	return false;
    }

}
