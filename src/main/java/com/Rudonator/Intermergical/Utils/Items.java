package com.Rudonator.Intermergical.Utils;

import com.Rudonator.Intermergical.Items.ItemResearchProgress;

import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Items {

	public static final Item research_progress = new ItemResearchProgress();
	
	public static void loadItems(){
		add(research_progress);
	}
	
	private static void add(Item item){
		GameRegistry.registerItem(item, item.getUnlocalizedName(), Constants.MODID);
	}
}
