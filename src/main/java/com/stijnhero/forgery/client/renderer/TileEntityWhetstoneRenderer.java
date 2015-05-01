package com.stijnhero.forgery.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class TileEntityWhetstoneRenderer extends TileEntitySpecialRenderer {
    private final ModelWhetstoneBase model1;
    
    public TileEntityWhetstoneRenderer() {
            this.model1 = new ModelWhetstoneBase();
    }

	@Override
	public void renderTileEntityAt(TileEntity te, double posX,
			double posY, double posZ, float p_180535_8_, int p_180535_9_) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) posX + 0.5F, (float) posY + 1.5F, (float) posZ + 0.5F);
		ResourceLocation textures = (new ResourceLocation("Texture")); 
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);                       
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model1.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
		
	}
}