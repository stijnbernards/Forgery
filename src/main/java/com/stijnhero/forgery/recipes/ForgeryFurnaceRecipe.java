package com.stijnhero.forgery.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class ForgeryFurnaceRecipe {

	public static List<ForgeryFurnaceRecipe> recipes = new ArrayList<ForgeryFurnaceRecipe>();
	
	public static void addRecipe(ForgeryFurnaceRecipe recipe){
		recipes.add(recipe);
	}
	
	public static ForgeryFurnaceRecipe getRecipe(ItemStack item){
		for(int i = 0; i < recipes.size(); i++){
			if(recipes.get(i).item.equals(item)){
				return recipes.get(i);
			}
		}
		return null;
	}
	
	public Item item;
	public Fluid fluid;
	public double heat;
	
	public ForgeryFurnaceRecipe(Item item, Fluid fluid, int heat){
		this.item = item;
		this.fluid = fluid;
		this.heat = heat;
	}
}
