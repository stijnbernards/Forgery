package com.stijnhero.forgery.common.tileentity;

import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

public class TileEntityWhetstone extends TileEntity implements IUpdatePlayerListBox {

	private boolean isActive;
	private float rotate = 0.0F;
	public float left = 0.0F;

	public TileEntityWhetstone() {

	}

	public void setRotate() {
//		this.rotate += 0.5F;
	}

	public float getRotate() {
		return rotate;
	}

	public boolean getActive() {
		return isActive;
	}

	@Override
	public void update() {
		if (this.worldObj.isRemote) {
			if (this.rotate >= 360) {
				this.rotate = 0;
			}
			this.rotate += 30;
		}
	}

}
