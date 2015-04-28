package com.stijnhero.forgery;

import scala.ref.Reference;

import com.stijnhero.forgery.common.item.ItemForgeryAxe;
import com.stijnhero.forgery.common.item.ItemForgeryHoe;
import com.stijnhero.forgery.common.item.ItemForgeryPickaxe;
import com.stijnhero.forgery.common.item.ItemForgerySpade;
import com.stijnhero.forgery.common.item.ItemForgerySword;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ForgeryItems {

	public static ToolMaterial FLINT = EnumHelper.addToolMaterial("FLINT", 1, 20, 1.5F, 0.5F, 0);
	
	public static Item ItemFlintAxe;
	public static Item ItemFlintPickAxe;
	public static Item ItemFlintSpade;
	public static Item ItemFlintHoe;
	public static Item ItemFlintSword;
	
	public static void Init(){
		ItemFlintAxe = new ItemForgeryAxe(FLINT).setUnlocalizedName("flintaxe");
		ItemFlintPickAxe = new ItemForgeryPickaxe(FLINT).setUnlocalizedName("flintpickaxe");
		ItemFlintSpade = new ItemForgerySpade(FLINT).setUnlocalizedName("flintspade");
		ItemFlintHoe = new ItemForgeryHoe(FLINT).setUnlocalizedName("flinthoe");
		ItemFlintSword = new ItemForgerySword(FLINT).setUnlocalizedName("flintsword");
	}
	
	public static void RegisterItemsInPre(){
		GameRegistry.registerItem(ItemFlintAxe, "flintaxe");
		GameRegistry.registerItem(ItemFlintPickAxe, "flintpickaxe");
		GameRegistry.registerItem(ItemFlintSpade, "flintspade");
		GameRegistry.registerItem(ItemFlintHoe, "flinthoe");
		GameRegistry.registerItem(ItemFlintSword, "flintsword");
	}
	
	public static void RegisterItemModels(){
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
    	renderItem.getItemModelMesher().register(ItemFlintAxe, 0, new ModelResourceLocation("Forgery:flintaxe", "inventory"));
    	renderItem.getItemModelMesher().register(ItemFlintPickAxe, 0, new ModelResourceLocation("Forgery:flintpickaxe", "inventory"));
    	renderItem.getItemModelMesher().register(ItemFlintSpade, 0, new ModelResourceLocation("Forgery:flintspade", "inventory"));
    	renderItem.getItemModelMesher().register(ItemFlintHoe, 0, new ModelResourceLocation("Forgery:flinthoe", "inventory"));
    	renderItem.getItemModelMesher().register(ItemFlintSword, 0, new ModelResourceLocation("Forgery:flintsword", "inventory"));
	}
	
}
