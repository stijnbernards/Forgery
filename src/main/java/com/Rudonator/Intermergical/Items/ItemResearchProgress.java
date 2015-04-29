package com.Rudonator.Intermergical.Items;

import com.Rudonator.Intermergical.Intermergical;
import com.Rudonator.Intermergical.Utils.Guis;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemResearchProgress extends ItemIntermergical {

	public ItemResearchProgress() {
		super("Research Progress");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		if(world.isRemote){
			player.openGui(Intermergical.instance, Guis.RESEARCH_PROGRESS_ID, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
		return super.onItemRightClick(itemstack, world, player);
	}

}
