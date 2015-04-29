package com.stijnhero.forgery;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.stijnhero.forgery.client.renderer.ColourThreshold;
import com.stijnhero.forgery.common.worldgen.WorldGen;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.config.Configuration;

public class ForgeryConfiguration {

	private static final String DEFAULT_COLOR_LIST = "100,f; 80,7; 60,e; 40,6; 25,c; 10,4";
	public static final List<ColourThreshold> colorList = new ArrayList<ColourThreshold>();

	public static Configuration config;
	public static final String CATEGORY_GAMEPLAY = "gameplay";
	
	public static void init(File configFile)
	{
		for (String s : DEFAULT_COLOR_LIST.split(";"))
		{
			String[] ct = s.split(",");
			colorList.add(new ColourThreshold(Integer.valueOf(ct[0].trim()), ct[1].trim()));
		}


		config = new Configuration(configFile);

		try
		{
			config.load();
			syncConfig();

		} catch (Exception e)
		{
		} finally
		{
			config.save();
		}
	}
	
	public static void syncConfig()
	{	
		//Worldgen
		WorldGen.CopperOreChance = config.get("Orespawns", "Copper ore", "50").getInt();
		WorldGen.TinOreChance = config.get("Orespawns", "Tin ore", "50").getInt();
		
		//Recipes to remove
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Wooden Axe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Wooden Hoe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Wooden Pickaxe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Wooden Shovel", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Wooden Sword", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Stone Axe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Stone Hoe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Stone Pickaxe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Stone Shovel", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Stone Sword", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Iron Axe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Iron Hoe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Iron Pickaxe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Iron Shovel", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Iron Sword", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Golden Axe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Golden Hoe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Golden Pickaxe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Golden Shovel", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Golden Sword", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Diamond Axe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Diamond Hoe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Diamond Pickaxe", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Diamond Shovel", true).getBoolean());
		ForgeryCrafting.RemoveItemArray.add(config.get("Removed recipes", "Diamond Sword", true).getBoolean());
		
		//Allow Harvest
		ForgeryBlocks.HarvestWood = config.get("Other", "Allow harvest wood", true).getBoolean();
		
		config.save();
	}
	
	public static void set(String categoryName, String propertyName, String newValue)
	{

		config.load();
		if (config.getCategoryNames().contains(categoryName))
		{
			if (config.getCategory(categoryName).containsKey(propertyName))
			{
				config.getCategory(categoryName).get(propertyName).set(newValue);
			}
		}
		config.save();
	}
}
