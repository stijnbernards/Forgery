package com.stijnhero.forgery.common.item;

import com.stijnhero.forgery.Forgery;

import net.minecraft.item.ItemAxe;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemForgeryAxe extends ItemAxe{
	
	public ItemForgeryAxe(ToolMaterial material) {
		super(material);
		this.setCreativeTab(Forgery.Forgery);
	}

}
