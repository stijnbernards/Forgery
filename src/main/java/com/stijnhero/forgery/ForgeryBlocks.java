package com.stijnhero.forgery;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.stijnhero.forgery.client.renderer.ModelWhetstoneBase;
import com.stijnhero.forgery.common.block.BlockFluidChannel;
import com.stijnhero.forgery.common.block.BlockForgeryFurnace;
import com.stijnhero.forgery.common.block.BlockHeater;
import com.stijnhero.forgery.common.block.BlockLiquidOre;
import com.stijnhero.forgery.common.block.BlockOre;
import com.stijnhero.forgery.common.block.BlockWhetStone;
import com.stijnhero.forgery.common.tileentity.TileEntityFluidChannel;
import com.stijnhero.forgery.common.tileentity.TileEntityForgeryFurnace;
import com.stijnhero.forgery.common.tileentity.TileEntityHeater;
import com.stijnhero.forgery.common.worldgen.WorldGen;

public class ForgeryBlocks {
	
	public static Block OreCopper;
	public static Block OreTin;
	public static Block ClayHeater;
	public static Block ClayHeaterLit;
	public static Block BronzeHeater;
	public static Block LiquidCopper;
	public static Block ForgeryFurnace;
	public static Block LiquidCopperBlock;
	public static Block FluidChannel;
	public static Block WhetStone;
	
	//For testing purposes
	public static Block FluidChannelTest; 
	
	public static boolean HarvestWood = true;
	public static boolean LeavesDrop = true;
	
	public static void WorldGen(){
		GameRegistry.registerWorldGenerator(new WorldGen(), 1);
	}
	
	public static void Init(){
		WhetStone = new BlockWhetStone(Material.wood).setUnlocalizedName("whetstone");
		LiquidCopperBlock = new BlockLiquidOre(ForgeryFluids.LiquidCopper, Material.lava).setUnlocalizedName("liquidcopper");
		OreCopper = new BlockOre(Material.rock, 1).setHardness(3.0F).setUnlocalizedName("orecopper");
		OreTin = new BlockOre(Material.rock, 1).setHardness(3.0F).setUnlocalizedName("oretin");
		ClayHeater = new BlockHeater(Material.rock, 100, 100).setHardness(3.0F).setUnlocalizedName("clayheater");
		ClayHeaterLit = new BlockHeater(Material.rock, 100, 100).setHardness(3.0F).setLightLevel(3).setUnlocalizedName("clayheaterlit");
		BronzeHeater = new BlockHeater(Material.rock, 500, 500).setHardness(3.0F).setUnlocalizedName("bronzeheater");
		ForgeryFurnace = new BlockForgeryFurnace(Material.rock).setHardness(3.0F).setUnlocalizedName("forgery_furnace");
		FluidChannel = new BlockFluidChannel(false).setHardness(3.0F).setUnlocalizedName("fluidchannel");

		FluidChannelTest = new BlockFluidChannel(true).setHardness(3.0F).setUnlocalizedName("fluidchanneltest");
	}
	
	public static void RegisterBlocksInPre(){
		GameRegistry.registerBlock(ForgeryBlocks.WhetStone, "whetstone");
		GameRegistry.registerBlock(ForgeryBlocks.OreCopper, "orecopper");
		GameRegistry.registerBlock(ForgeryBlocks.OreTin, "oretin");
		GameRegistry.registerBlock(ForgeryBlocks.ClayHeater, "clayheater");
		GameRegistry.registerBlock(ForgeryBlocks.ClayHeaterLit, "clayheaterlit");
		GameRegistry.registerBlock(ForgeryBlocks.ForgeryFurnace, "furnace");
		GameRegistry.registerBlock(ForgeryBlocks.BronzeHeater, "bronzeheater");
		GameRegistry.registerBlock(ForgeryBlocks.LiquidCopperBlock, "liquidcopper");
		GameRegistry.registerBlock(ForgeryBlocks.FluidChannel, "fluidchannel");
		
		GameRegistry.registerBlock(ForgeryBlocks.FluidChannelTest, "fluidchanneltest");
		
		GameRegistry.registerTileEntity(TileEntityHeater.class, "forgery.clayheater");
		GameRegistry.registerTileEntity(TileEntityHeater.class, "forgery.clayheaterlit");
		GameRegistry.registerTileEntity(TileEntityForgeryFurnace.class, "forgery.furnace");
		GameRegistry.registerTileEntity(TileEntityHeater.class, "forgery.bronzeheater");
		GameRegistry.registerTileEntity(TileEntityFluidChannel.class, "forgery.fluidchannel");
	}
	
	public static void EditBlocks(){
		if(HarvestWood)
			MinecraftForge.EVENT_BUS.register(new ForgeryEvents());
	}
	
	public static void RegisterItemBlockModels(){
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(OreCopper), 0, new ModelResourceLocation("forgery:orecopper", "inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(OreTin), 0, new ModelResourceLocation("forgery:oretin", "inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(ClayHeater), 0, new ModelResourceLocation("forgery:clayheater", "inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(ClayHeaterLit), 0, new ModelResourceLocation("forgery:clayheaterlit", "inventory"));
	}
}
