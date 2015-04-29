package com.Rudonator.Intermergical.proxy;

import com.Rudonator.Intermergical.Gui.GuiResearchProgress;
import com.Rudonator.Intermergical.Utils.Guis;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Client implements Proxy {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Guis.RESEARCH_PROGRESS_ID){
			return new GuiResearchProgress();
		}
		return null;
	}
}
