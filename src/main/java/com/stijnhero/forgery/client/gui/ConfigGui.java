package com.stijnhero.forgery.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

import static com.stijnhero.forgery.ForgeryConfiguration.config;

public class ConfigGui extends GuiConfig{
	
	public ConfigGui(GuiScreen parentScreen) {
		super(parentScreen, getConfigElements(parentScreen), "Stijnhero", false, false, "Forgery Configuration");
	}
	
	@SuppressWarnings("rawtypes")
	private static List<IConfigElement> getConfigElements(GuiScreen parent) {
		List<IConfigElement> list = new ArrayList<IConfigElement>();

		list.add(new ConfigElement(config.getCategory("Orespawns".toLowerCase())));
		list.add(new ConfigElement(config.getCategory("Removed recipes".toLowerCase())));
		list.add(new ConfigElement(config.getCategory("Other".toLowerCase())));

		return list;
	}
}
