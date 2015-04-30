package com.stijnhero.forgery.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.stijnhero.forgery.client.gui.container.ContainerForgeryFurnace;
import com.stijnhero.forgery.common.tileentity.TileEntityForgeryFurnace;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileentity = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID){
			case Guis.FORGERY_FURNACE:
				if(tileentity instanceof TileEntityForgeryFurnace){
					return new ContainerForgeryFurnace((TileEntityForgeryFurnace)tileentity, player.inventory);
				}
			}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) { 
		TileEntity tileentity = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID){
			case Guis.FORGERY_FURNACE:
				if(tileentity instanceof TileEntityForgeryFurnace){
					return new GuiForgeryFurnace((TileEntityForgeryFurnace)tileentity, player.inventory);
				}
			}
		return null;
	}
}
