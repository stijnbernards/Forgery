package com.Rudonator.Intermergical;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.Rudonator.Intermergical.Utils.Constants;
import com.Rudonator.Intermergical.Utils.Items;
import com.Rudonator.Intermergical.proxy.Proxy;

@Mod(modid = Constants.MODID, version = Constants.VERSION)
public class Intermergical {

	@Instance
	public static Intermergical instance;
	
	@SidedProxy(clientSide = Constants.CLIENT_PROXY, serverSide = Constants.SERVER_PROXY)
	public static Proxy proxy;
	
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		Items.loadItems();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
	}
}
