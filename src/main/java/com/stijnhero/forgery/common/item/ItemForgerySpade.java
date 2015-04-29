package com.stijnhero.forgery.common.item;

import com.stijnhero.forgery.Forgery;

import net.minecraft.item.ItemSpade;

public class ItemForgerySpade extends ItemSpade{

	public ItemForgerySpade(ToolMaterial material) {
		super(material);
		this.setCreativeTab(Forgery.Forgery);
	}

}
