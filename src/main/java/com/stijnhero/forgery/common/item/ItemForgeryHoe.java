package com.stijnhero.forgery.common.item;

import com.stijnhero.forgery.Forgery;

import net.minecraft.item.ItemHoe;

public class ItemForgeryHoe extends ItemHoe{

	public ItemForgeryHoe(ToolMaterial material) {
		super(material);
		this.setCreativeTab(Forgery.Forgery);
	}

}
