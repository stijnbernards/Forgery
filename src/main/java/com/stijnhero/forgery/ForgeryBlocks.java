package com.stijnhero.forgery;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.stijnhero.forgery.common.block.BlockFluidChannel;
import com.stijnhero.forgery.common.block.BlockFurnace;
import com.stijnhero.forgery.common.block.BlockHeater;
import com.stijnhero.forgery.common.block.BlockLiquidOre;
import com.stijnhero.forgery.common.block.BlockOre;
import com.stijnhero.forgery.common.tileentity.TileEntityFluidChannel;
import com.stijnhero.forgery.common.tileentity.TileEntityFurnace;
import com.stijnhero.forgery.common.tileentity.TileEntityHeater;
import com.stijnhero.forgery.common.worldgen.WorldGen;

public class ForgeryBlocks {
	public static Block OreCopper;
	public static Block OreTin;
	public static Block ClayHeater;
	public static Block Furnace;
	public static Block BronzeHeater;
	public static Block LiquidCopperBlock;
	public static Block FluidChannel;
	
	//For testing purposes
	public static Block FluidChannelTest; 
	
	public static boolean HarvestWood = true;
	public static boolean LeavesDrop = true;
	
	public static void WorldGen(){
		GameRegistry.registerWorldGenerator(new WorldGen(), 1);
	}
	
	public static void Init(){
		LiquidCopperBlock = new BlockLiquidOre(ForgeryFluids.LiquidCopper, Material.lava).setUnlocalizedName("liquidcopper");
		OreCopper = new BlockOre(Material.rock, 1).setHardness(3.0F).setUnlocalizedName("orecopper");
		OreTin = new BlockOre(Material.rock, 1).setHardness(3.0F).setUnlocalizedName("oretin");
		ClayHeater = new BlockHeater(Material.rock, 100, 100).setHardness(3.0F).setUnlocalizedName("clayheater");
		Furnace = new BlockFurnace(Material.rock).setHardness(3.0F).setUnlocalizedName("furnace");
		BronzeHeater = new BlockHeater(Material.rock, 500, 500).setHardness(3.0F).setUnlocalizedName("bronzeheater");
		FluidChannel = new BlockFluidChannel(false).setHardness(3.0F).setUnlocalizedName("fluidchannel");

		FluidChannelTest = new BlockFluidChannel(true).setHardness(3.0F).setUnlocalizedName("fluidchanneltest");
	}
	
	public static void RegisterBlocksInPre(){
		GameRegistry.registerBlock(ForgeryBlocks.OreCopper, "orecopper");
		GameRegistry.registerBlock(ForgeryBlocks.OreTin, "oretin");
		GameRegistry.registerBlock(ForgeryBlocks.ClayHeater, "clayheater");
		GameRegistry.registerBlock(ForgeryBlocks.Furnace, "furnace");
		GameRegistry.registerBlock(ForgeryBlocks.BronzeHeater, "bronzeheater");
		GameRegistry.registerBlock(ForgeryBlocks.LiquidCopperBlock, "liquidcopper");
		GameRegistry.registerBlock(ForgeryBlocks.FluidChannel, "fluidchannel");
		
		GameRegistry.registerBlock(ForgeryBlocks.FluidChannelTest, "fluidchanneltest");
		
		GameRegistry.registerTileEntity(TileEntityHeater.class, "forgery.clayheater");
		GameRegistry.registerTileEntity(TileEntityHeater.class, "forgery.bronzeheater");
		GameRegistry.registerTileEntity(TileEntityFurnace.class, "forgery.furnace");
		GameRegistry.registerTileEntity(TileEntityFluidChannel.class, "forgery.fluidchannel");
	}
	
	public static void EditBlocks(){
		if(HarvestWood)
			MinecraftForge.EVENT_BUS.register(new ForgeryEvents());
	}
}
