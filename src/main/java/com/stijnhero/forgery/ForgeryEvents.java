package com.stijnhero.forgery;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeryEvents {
	
	@SubscribeEvent
	public void onBlockBreak(HarvestDropsEvent e) { 
	    if (!e.world.isRemote) {
	        Block b = e.state.getBlock();

	        if (b == Blocks.log || b == Blocks.log2) {
	            if (e.harvester.getCurrentEquippedItem() == null) {
	                e.drops.clear();
	            }
	            else if (!(e.harvester.getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
	                e.drops.clear();
	            }
	        }
	        
	        if(b == Blocks.leaves || b == Blocks.leaves2){
	        	e.dropChance = 0.3F;
	        	e.drops.add(new ItemStack(Items.stick));
	        }
	    }
	}

}
