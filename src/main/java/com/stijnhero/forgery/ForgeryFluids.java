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
	public static Fluid Slag;
	
	public static void Init(){
		LiquidCopper = new Fluid("fluidliquidcopper").setUnlocalizedName("fluidliquidcopper");
		LiquidTin = new Fluid("fluidliquidtin").setUnlocalizedName("fluidliquidtin");
		Slag = new Fluid("fluidslag").setUnlocalizedName("fluidslag");
	}
	
	public static void RegisterFluidsInPre(){
		FluidRegistry.registerFluid(LiquidCopper);
		FluidRegistry.registerFluid(LiquidTin);
		FluidRegistry.registerFluid(Slag);
	}
	
	public static void RegisterFluidIcons(){
		TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
		TextureAtlasSprite copper_sprite = map.registerSprite(new ResourceLocation("forgery", "textures/gui/forgery_furnace.png"));
		LiquidCopper.setStillIcon(copper_sprite);
		LiquidCopper.setFlowingIcon(copper_sprite);
		
		TextureAtlasSprite tin_sprite = map.registerSprite(new ResourceLocation("forgery", "textures/gui/forgery_furnace.png"));
		LiquidTin.setStillIcon(tin_sprite);
		LiquidTin.setFlowingIcon(tin_sprite);
		TextureAtlasSprite slag_sprite = map.registerSprite(new ResourceLocation("forgery", "textures/gui/forgery_furnace.png"));
		Slag.setStillIcon(slag_sprite);
		Slag.setFlowingIcon(slag_sprite);
	}
}
