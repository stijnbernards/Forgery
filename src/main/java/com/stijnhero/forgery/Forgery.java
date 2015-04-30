package com.stijnhero.forgery;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.stijnhero.forgery.client.gui.GuiHandler;
import com.stijnhero.forgery.proxy.CommonProxy;
import com.stijnhero.forgery.recipes.ForgeryFurnaceRecipe;

@Mod(modid = Forgery.MODID, version = Forgery.VERSION, guiFactory = "com.stijnhero.forgery.client.gui.ConfigGuiFactory")
public class Forgery
{
    public static final String MODID = "forgery";
    public static final String VERSION = "0.1";
    public static final String NAME = "Forgery";
    
    public static final String CLIENT_PROXY = "com.stijnhero.forgery.proxy.ClientProxy";
    public static final String SERVER_PROXY = "com.stijnhero.forgery.proxy.CommonProxy";
    
    @Instance
    public static Forgery instance;
    
    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static CommonProxy proxy;

    //Creative tabs
    public static CreativeTabs Forgery = new CreativeTabs("Forgery"){
		public Item getTabIconItem(){
			return Items.iron_ingot;
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){		
		ForgeryConfiguration.init(new File(event.getModConfigurationDirectory(), "Forgery.cfg"));
		
		ForgeryFluids.Init();
		ForgeryFluids.RegisterFluidsInPre();
		
		ForgeryBlocks.Init();
		ForgeryBlocks.RegisterBlocksInPre();
		ForgeryBlocks.WorldGen();
		
		ForgeryItems.Init();
		ForgeryItems.RegisterItemsInPre();
		
		ForgeryFurnaceRecipe.addRecipe(new ForgeryFurnaceRecipe(new ItemStack(ForgeryBlocks.OreCopper), ForgeryFluids.LiquidCopper, 50, 100, 40));
		ForgeryFurnaceRecipe.addRecipe(new ForgeryFurnaceRecipe(new ItemStack(ForgeryBlocks.OreTin), ForgeryFluids.LiquidTin, 80, 100, 60));
		
		proxy.registerRenderers();
	}
	
    @EventHandler
    public void load(FMLInitializationEvent event){
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

    	if(event.getSide() == Side.CLIENT)
    	{
    	    ForgeryItems.RegisterItemModels();
    	}
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
		ForgeryCrafting.Init();

		ForgeryBlocks.EditBlocks();
    }
}
