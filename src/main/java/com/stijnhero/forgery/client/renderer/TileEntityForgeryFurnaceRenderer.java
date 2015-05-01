package com.stijnhero.forgery.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

public class TileEntityForgeryFurnaceRenderer extends TileEntitySpecialRenderer {
    private final ModelForgeryFurnace model;
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
    public TileEntityForgeryFurnaceRenderer() {
            this.model = new ModelForgeryFurnace();
    }

	@Override
	public void renderTileEntityAt(TileEntity te, double posX,
			double posY, double posZ, float p_180535_8_, int p_180535_9_) {
		EnumFacing facing = (EnumFacing) te.getWorld().getBlockState(te.getPos()).getValue(FACING);
		
		
        GL11.glPushMatrix();
        GL11.glTranslatef((float) posX + 0.5F, (float) posY + 1.5F, (float) posZ + 0.5F);
		ResourceLocation textures = (new ResourceLocation("Texture")); 
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);                       
        GL11.glPushMatrix();
        GL11.glRotatef(180F, getRotationFromFacing(facing), 0.0F, 1.0F);
        this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
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