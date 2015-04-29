package com.Rudonator.Intermergical.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.Rudonator.Intermergical.Gui.GuiResearchProgress;
import com.Rudonator.Intermergical.Utils.Guis;

public class Server implements Proxy {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) { return null;	}

}
