package com.stijnhero.forgery.common.tileentity;

import net.minecraft.tileentity.TileEntity;

public class TileEntityWhetstone extends TileEntity{
	
	private boolean isActive;
	private float rotate = 0.0F;
	public float left = 0.0F;
	
	public TileEntityWhetstone(){
		
	}
	
	public void setRotate(){
		this.rotate += 0.5F;
	}
	
	public float getRotate(){
		return rotate;
	}
	
	public boolean getActive(){
		return isActive;
	}

}
