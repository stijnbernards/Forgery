package com.stijnhero.forgery.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.stijnhero.forgery.ForgeryBlocks;
import com.stijnhero.forgery.common.block.BlockForgeryFurnace;

public class TileEntityWhetstoneRenderer extends TileEntitySpecialRenderer {
    private final ModelWhetstoneBase model1;
    private final ModelForgeryFurnace model2;
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
    public TileEntityWhetstoneRenderer() {
            this.model1 = new ModelWhetstoneBase();
            this.model2 = new ModelForgeryFurnace();
    }

	@Override
	public void renderTileEntityAt(TileEntity te, double posX,
			double posY, double posZ, float p_180535_8_, int p_180535_9_) {
		
		EnumFacing facing = (EnumFacing) te.getWorld().getBlockState(te.getPos()).getValue(FACING);
		
        GL11.glPushMatrix();
        GL11.glTranslatef((float) posX + 0.5F, (float) posY + 1.5F, (float) posZ + 0.5F);
		ResourceLocation texture1 = (new ResourceLocation("Texture")); 
        Minecraft.getMinecraft().renderEngine.bindTexture(texture1);                       
        GL11.glPushMatrix();
        GL11.glRotatef(180F, getRotationFromFacing(facing), 0.0F, 1.0F);
        this.model1.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float) posX + 0.5F, (float) posY + 1.5F, (float) posZ + 0.5F);
        ResourceLocation texture2 = (new ResourceLocation("Texture")); 
        Minecraft.getMinecraft().renderEngine.bindTexture(texture2);                       
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 20F, 0.0F, 1.0F);
        this.model2.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
	}
	
	public float getRotationFromFacing(EnumFacing facing){
		if(facing == EnumFacing.NORTH)
			return 0.0F;
		else if(facing == EnumFacing.EAST)
			return -1.0F;
		else if(facing == EnumFacing.SOUTH)
			return 180F;
		else if(facing == EnumFacing.WEST)
			return 1.0F;
		
		return 0.0F;
	}
}