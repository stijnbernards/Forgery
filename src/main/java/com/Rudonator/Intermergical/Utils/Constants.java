package com.Rudonator.Intermergical.Utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Constants {

	public static final String MODID = "intermergical";
	public static final String VERSION = "0.0.1";
	public static final String NAME = "Intermergical";
	
	public static final String CLIENT_PROXY = "com.Rudonator.Intermergical.proxy.Client";
	public static final String SERVER_PROXY = "com.Rudonator.Intermergical.proxy.Server";
	
	public static CreativeTabs TAB = new CreativeTabs(NAME){

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Items.writable_book;
		}
		
	};
}
