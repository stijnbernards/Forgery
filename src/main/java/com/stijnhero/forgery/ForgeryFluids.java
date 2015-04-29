package com.stijnhero.forgery;

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
}
