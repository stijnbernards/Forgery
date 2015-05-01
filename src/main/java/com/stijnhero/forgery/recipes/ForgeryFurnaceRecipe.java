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
		if(item == null) return null;
		for(int i = 0; i < recipes.size(); i++){
			if(recipes.get(i).item.isItemEqual(item)){
				return recipes.get(i);
			}
		}
		return null;
	}
	
	public ItemStack item;
	public Fluid fluid;
	public double heat;
	public int amount;
	public int duration;
	
	/**
	 * @param item 
	 * @param fluid Result
	 * @param heat Min required heat
	 * @param amount Output fluid
	 * @param duration Time to craft
	 */
	public ForgeryFurnaceRecipe(ItemStack item, Fluid fluid, double heat, int amount, int duration){
		this.item = item;
		this.fluid = fluid;
		this.heat = heat;
		this.amount = amount;
		this.duration = duration;
	}
}
