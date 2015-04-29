package com.stijnhero.forgery.common.item;

import com.stijnhero.forgery.Forgery;

import net.minecraft.item.ItemPickaxe;

public class ItemForgeryPickaxe extends ItemPickaxe{

	public ItemForgeryPickaxe(ToolMaterial material) {
		super(material);
		this.setCreativeTab(Forgery.Forgery);
	}

}
