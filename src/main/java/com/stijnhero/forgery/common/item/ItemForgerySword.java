package com.stijnhero.forgery.common.item;

import com.stijnhero.forgery.Forgery;

import net.minecraft.item.ItemSword;

public class ItemForgerySword extends ItemSword{

	public ItemForgerySword(ToolMaterial material) {
		super(material);
		this.setCreativeTab(Forgery.Forgery);
	}

}
