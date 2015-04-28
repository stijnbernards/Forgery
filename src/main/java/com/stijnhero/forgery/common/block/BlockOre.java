package com.stijnhero.forgery.common.block;

import com.stijnhero.forgery.Forgery;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockOre extends Block{

	public BlockOre(Material material, int Harvestlevel) {
		super(material);
		this.setCreativeTab(Forgery.Forgery);
		this.setHarvestLevel("pickaxe", Harvestlevel);
	}
}