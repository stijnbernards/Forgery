package com.stijnhero.forgery;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ForgeryCrafting {
	
	public static ArrayList<Boolean> RemoveItemArray = new ArrayList<Boolean>();
	
	public static void Init(){
		ArrayList<ItemStack> ItemsToRemove = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Items.wooden_axe), new ItemStack(Items.wooden_hoe), new ItemStack(Items.wooden_pickaxe), new ItemStack(Items.wooden_shovel), new ItemStack(Items.wooden_sword), new ItemStack(Items.stone_axe), new ItemStack(Items.stone_hoe), new ItemStack(Items.stone_pickaxe), new ItemStack(Items.stone_shovel), new ItemStack(Items.stone_sword), new ItemStack(Items.iron_axe), new ItemStack(Items.iron_hoe), new ItemStack(Items.iron_pickaxe), new ItemStack(Items.iron_shovel), new ItemStack(Items.iron_sword), new ItemStack(Items.golden_axe), new ItemStack(Items.golden_hoe), new ItemStack(Items.golden_pickaxe), new ItemStack(Items.golden_shovel), new ItemStack(Items.golden_sword), new ItemStack(Items.diamond_axe), new ItemStack(Items.diamond_hoe), new ItemStack(Items.diamond_pickaxe), new ItemStack(Items.diamond_shovel), new ItemStack(Items.diamond_sword)));
		
		for (int item = 0; item < RemoveItemArray.size(); item++){
			if(RemoveItemArray.get(item)){
				RemoveRecipe(ItemsToRemove.get(item));
			}
		}
		
		GameRegistry.addShapedRecipe(new ItemStack(ForgeryItems.ItemFlintAxe), new Object[] {"##", "W#", 'W', Items.stick, '#', Items.flint});
		GameRegistry.addShapedRecipe(new ItemStack(ForgeryItems.ItemFlintAxe), new Object[] {"##", "#W", 'W', Items.stick, '#', Items.flint});
		GameRegistry.addShapedRecipe(new ItemStack(ForgeryItems.ItemFlintPickAxe), new Object[] {"##", "W ", 'W', Items.stick, '#', Items.flint});
		GameRegistry.addShapedRecipe(new ItemStack(ForgeryItems.ItemFlintPickAxe), new Object[] {"##", " W", 'W', Items.stick, '#', Items.flint});
		GameRegistry.addShapedRecipe(new ItemStack(ForgeryItems.ItemFlintSpade), new Object[] {"#", "W", 'W', Items.stick, '#', Items.flint});
	}
	
	public static void RemoveRecipe(ItemStack resultItem){
	    ArrayList recipes = (ArrayList) CraftingManager.getInstance().getRecipeList();

	    for (int scan = 0; scan < recipes.size(); scan++)
	    {
	        IRecipe tmpRecipe = (IRecipe) recipes.get(scan);
	        ItemStack recipeResult = tmpRecipe.getRecipeOutput();
	        if (ItemStack.areItemStacksEqual(resultItem, recipeResult))
	        {
	            recipes.remove(scan);
	        }
	    }
	}

}
