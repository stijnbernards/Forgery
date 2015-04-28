package com.stijnhero.forgery;

import com.stijnhero.forgery.common.block.BlockHeater;
import com.stijnhero.forgery.common.block.BlockOre;
import com.stijnhero.forgery.common.tileentity.TileEntityHeater;
import com.stijnhero.forgery.common.worldgen.WorldGen;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ForgeryBlocks {

	public static Block OreCopper;
	public static Block OreTin;
	public static Block Heater;
	
	public static boolean HarvestWood = true;
	public static boolean LeavesDrop = true;
	
	public static void WorldGen(){
		GameRegistry.registerWorldGenerator(new WorldGen(), 1);
	}
	
	public static void Init(){
		OreCopper = new BlockOre(Material.rock, 1).setHardness(3.0F).setUnlocalizedName("orecopper");
		OreTin = new BlockOre(Material.rock, 1).setHardness(3.0F).setUnlocalizedName("oretin");
		Heater = new BlockHeater(Material.rock).setHardness(3.0F).setUnlocalizedName("heater");
	}
	
	public static void RegisterBlocksInPre(){
		GameRegistry.registerBlock(ForgeryBlocks.OreCopper, "orecopper");
		GameRegistry.registerBlock(ForgeryBlocks.OreTin, "oretin");
		GameRegistry.registerBlock(ForgeryBlocks.Heater, "heater");
		
		GameRegistry.registerTileEntity(TileEntityHeater.class, "forgery.heater");
	}
	
	public static void EditBlocks(){
		if(HarvestWood)
			MinecraftForge.EVENT_BUS.register(new ForgeryEvents());
	}
}
