package com.Rudonator.Intermergical.Items;

import com.Rudonator.Intermergical.Utils.Constants;

import net.minecraft.item.Item;

public class ItemIntermergical extends Item {

	public ItemIntermergical(String name){
		this.setCreativeTab(Constants.TAB);
		this.setUnlocalizedName("item_" + Constants.MODID + "_" + name.toLowerCase().replace(" ", "_"));
	}
}
