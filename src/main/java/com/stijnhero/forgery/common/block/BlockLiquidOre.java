package com.stijnhero.forgery.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.stijnhero.forgery.Forgery;

public class BlockLiquidOre extends BlockFluidClassic {

	public BlockLiquidOre(Fluid fluid, Material material) {
		super(fluid, material);
		setCreativeTab(Forgery.Forgery);
		stack = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
	}
}
