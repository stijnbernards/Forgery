package com.stijnhero.forgery;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ForgeryFluids {
	
	public static Fluid LiquidCopper;
	public static Fluid LiquidTin;
	
	public static void Init(){
		LiquidCopper = new Fluid("fluidliquidcopper").setUnlocalizedName("fluidliquidcopper");
		LiquidTin = new Fluid("fluidliquidtin").setUnlocalizedName("fluidliquidtin");
	}
	
	public static void RegisterFluidsInPre(){
		FluidRegistry.registerFluid(LiquidCopper);
		FluidRegistry.registerFluid(LiquidTin);
	}
	
	public static void RegisterFluidIcons(){
		TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
		TextureAtlasSprite copper_sprite = map.registerSprite(new ResourceLocation("forgery:fluidliquidcopper"));
		LiquidCopper.setStillIcon(copper_sprite);
		LiquidCopper.setFlowingIcon(copper_sprite);
	}
}
