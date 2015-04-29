package com.stijnhero.forgery;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

<<<<<<< HEAD
import com.stijnhero.forgery.common.block.BlockForgeryDynamicLiquid;
import com.stijnhero.forgery.common.block.BlockFurnace;
=======
import com.stijnhero.forgery.common.block.BlockForgeryFurnace;
>>>>>>> Ruud
import com.stijnhero.forgery.common.block.BlockHeater;
import com.stijnhero.forgery.common.block.BlockOre;
import com.stijnhero.forgery.common.tileentity.TileEntityForgeryFurnace;
import com.stijnhero.forgery.common.tileentity.TileEntityHeater;
import com.stijnhero.forgery.common.worldgen.WorldGen;

public class ForgeryBlocks {
	public static Block OreCopper;
	public static Block OreTin;
	public static Block ClayHeater;
<<<<<<< HEAD
	public static Block Furnace;
	public static Block BronzeHeater;
	public static Block LiquidCopper;
=======
	public static Block ForgeryFurnace;
>>>>>>> Ruud
	
	public static boolean HarvestWood = true;
	public static boolean LeavesDrop = true;
	
	public static void WorldGen(){
		GameRegistry.registerWorldGenerator(new WorldGen(), 1);
	}
	
	public static void Init(){
		LiquidCopper = new BlockForgeryDynamicLiquid(Material.lava);
		OreCopper = new BlockOre(Material.rock, 1).setHardness(3.0F).setUnlocalizedName("orecopper");
		OreTin = new BlockOre(Material.rock, 1).setHardness(3.0F).setUnlocalizedName("oretin");
<<<<<<< HEAD
		ClayHeater = new BlockHeater(Material.rock, 100, 100).setHardness(3.0F).setUnlocalizedName("clayheater");
		Furnace = new BlockFurnace(Material.rock).setHardness(3.0F).setUnlocalizedName("furnace");
		BronzeHeater = new BlockHeater(Material.rock, 500, 500).setHardness(3.0F).setUnlocalizedName("bronzeheater");
=======
		ClayHeater = new BlockHeater(Material.rock, 100).setHardness(3.0F).setUnlocalizedName("clayheater");
		ForgeryFurnace = new BlockForgeryFurnace(Material.rock).setHardness(3.0F).setUnlocalizedName("forgery_furnace");
>>>>>>> Ruud
	}
	
	public static void RegisterBlocksInPre(){
		GameRegistry.registerBlock(ForgeryBlocks.OreCopper, "orecopper");
		GameRegistry.registerBlock(ForgeryBlocks.OreTin, "oretin");
		GameRegistry.registerBlock(ForgeryBlocks.ClayHeater, "clayheater");
		GameRegistry.registerBlock(ForgeryBlocks.ForgeryFurnace, "furnace");
		
		GameRegistry.registerTileEntity(TileEntityHeater.class, "forgery.clayheater");
		GameRegistry.registerTileEntity(TileEntityForgeryFurnace.class, "forgery.furnace");

	}
	
	public static void EditBlocks(){
		if(HarvestWood)
			MinecraftForge.EVENT_BUS.register(new ForgeryEvents());
	}
}
