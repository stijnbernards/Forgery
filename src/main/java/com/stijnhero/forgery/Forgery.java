package com.stijnhero.forgery;

import java.io.File;
import java.io.InputStream;

import scala.ref.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = Forgery.MODID, version = Forgery.VERSION, guiFactory = "com.stijnhero.forgery.client.gui.ConfigGuiFactory")
public class Forgery
{
    public static final String MODID = "forgery";
    public static final String VERSION = "0.1";
    public static final String NAME = "Forgery";
    
    //Creative tabs
    public static CreativeTabs Forgery = new CreativeTabs("Forgery"){
		public Item getTabIconItem(){
			return Items.iron_ingot;
		}
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		ForgeryConfiguration.init(new File(event.getModConfigurationDirectory(), "Forgery.cfg"));
		
		ForgeryBlocks.Init();
		ForgeryBlocks.RegisterBlocksInPre();
		ForgeryBlocks.WorldGen();
		
		ForgeryItems.Init();
		ForgeryItems.RegisterItemsInPre();
	}
	
    @EventHandler
    public void load(FMLInitializationEvent event){
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
