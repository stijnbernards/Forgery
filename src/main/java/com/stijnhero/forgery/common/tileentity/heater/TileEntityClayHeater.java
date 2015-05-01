package com.stijnhero.forgery.common.tileentity.heater;

import com.stijnhero.forgery.ForgeryBlocks;

public class TileEntityClayHeater extends TileEntityHeater {

	public TileEntityClayHeater(){
		this.Multiplier = 100;
		this.MaxHeat = 100;
		this.normal_block = ForgeryBlocks.ClayHeater;
		this.active_block = ForgeryBlocks.ClayHeaterLit;
	}
}
