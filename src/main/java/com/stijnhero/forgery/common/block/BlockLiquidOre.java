package com.stijnhero.forgery.common.block;

import com.stijnhero.forgery.Forgery;

import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlockLiquidOre extends BlockFluidClassic{

	public BlockLiquidOre(Fluid fluid, Material material) {
		super(fluid, material);
		setCreativeTab(Forgery.Forgery);
        stack = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
	}
}
