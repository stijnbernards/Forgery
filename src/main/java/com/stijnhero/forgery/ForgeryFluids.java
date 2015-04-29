package com.stijnhero.forgery;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ForgeryFluids {
	
	public static Fluid LiquidCopper;
	
	public static void Init(){
		LiquidCopper = new Fluid("fluidliquidcopper").setUnlocalizedName("fluidliquidcopper");
	}
	
	public static void RegisterFluidsInPre(){
		FluidRegistry.registerFluid(LiquidCopper);
	}
}
