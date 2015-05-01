package com.stijnhero.forgery.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import com.stijnhero.forgery.client.gui.GuiForgeryFurnace;
import com.stijnhero.forgery.client.gui.Guis;
import com.stijnhero.forgery.client.renderer.TileEntityForgeryFurnaceRenderer;
import com.stijnhero.forgery.client.renderer.TileEntityWhetstoneRenderer;
import com.stijnhero.forgery.common.tileentity.TileEntityForgeryFurnace;
import com.stijnhero.forgery.common.tileentity.TileEntityWhetstone;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForgeryFurnace.class, new TileEntityForgeryFurnaceRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWhetstone.class, new TileEntityWhetstoneRenderer());
	}

}
