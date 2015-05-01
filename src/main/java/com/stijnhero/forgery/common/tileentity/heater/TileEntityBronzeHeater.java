package com.stijnhero.forgery.common.tileentity.heater;

import com.stijnhero.forgery.ForgeryBlocks;
import com.stijnhero.forgery.common.block.BlockHeater;

public class TileEntityBronzeHeater extends TileEntityHeater {

	public TileEntityBronzeHeater(){
		this.Multiplier = 200;
		this.MaxHeat = 1000;
		this.normal_block = ForgeryBlocks.BronzeHeater;
		this.active_block = ForgeryBlocks.BronzeHeaterLit;
	}
}
