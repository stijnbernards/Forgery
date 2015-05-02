package com.stijnhero.forgery.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fluids.Fluid;

public class RenderHelper {

	public static boolean renderFluid(Fluid fluid, double x, double y, double w, double h){
		if(fluid == null) return false;
		if(fluid.getStillIcon() == null) return false;
		
		TextureAtlasSprite textureAtlasSprite = fluid.getStillIcon();
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.startDrawingQuads();
		
		worldrenderer.addVertexWithUV(x, y, 0.0, (double) textureAtlasSprite.getMinU(), (double) textureAtlasSprite.getMaxV());
		worldrenderer.addVertexWithUV(x + w, y, 0.0, (double) textureAtlasSprite.getMaxU(), (double) textureAtlasSprite.getMaxV());
		worldrenderer.addVertexWithUV(x + w, y - h, 0.0, (double) textureAtlasSprite.getMaxU(), (double) textureAtlasSprite.getMinV());
		worldrenderer.addVertexWithUV(x, y - h, 0.0, (double) textureAtlasSprite.getMinU(), (double) textureAtlasSprite.getMinV());
		tessellator.draw();
		return true;
	}
}
