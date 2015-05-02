package com.stijnhero.forgery.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;

import org.lwjgl.opengl.GL11;

import com.stijnhero.forgery.ForgeryFluids;
import com.stijnhero.forgery.client.gui.container.ContainerForgeryFurnace;
import com.stijnhero.forgery.client.renderer.RenderHelper;
import com.stijnhero.forgery.common.tileentity.TileEntityForgeryFurnace;

public class GuiForgeryFurnace extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation("forgery", "textures/gui/forgery_furnace.png");
	private TileEntityForgeryFurnace tileentity;

	public GuiForgeryFurnace(TileEntityForgeryFurnace tileentity, InventoryPlayer inventory_player) {
		super(new ContainerForgeryFurnace(tileentity, inventory_player));
		this.tileentity = tileentity;
		this.xSize = 201;
		this.ySize = 195;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(texture);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
	}

	Random random = new Random();

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GL11.glColor4f(1, 1, 1, 1);
		this.mc.renderEngine.bindTexture(texture);

		this.drawTexturedModalRect(6 + this.tileentity.temperature, 101, 8, 195, 5, 7);

		for (int i = 0; i < 9; i++) {
			int c = 0;
			if (this.tileentity.durations[i] > 0) {
				c = this.tileentity.getCraftingProgressScaled(i, 15);
			}
			this.drawTexturedModalRect(12 + i * 18, 26, 0, 195, 8, c);
		}

		double s = 0;
		for (FluidTank tank : this.tileentity.tanks.values()) {
			if (tank == null)
				continue;
			if (tank.getFluid() == null)
				continue;
			if (tank.getFluid().getFluid() == null)
				continue;
			Fluid fluid = tank.getFluid().getFluid();

			double h = tank.getFluidAmount() * 52.0 / TileEntityForgeryFurnace.MAX_TANK + 1.0;
			if (h >= 52) {
				h = 53;
			}
			s -= h;
			if (!RenderHelper.renderFluid(fluid, 8, 96 + h + s, 160, h)) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
				Tessellator tessellator = Tessellator.getInstance();
				WorldRenderer worldrenderer = tessellator.getWorldRenderer();
				worldrenderer.startDrawingQuads();

				worldrenderer.addVertexWithUV(8, 96 + h + s, 0.0, 0, 0.0);
				worldrenderer.addVertexWithUV(8 + 160, 96 + h + s, 0.0, 0.0, 0.0);
				worldrenderer.addVertexWithUV(8 + 160, 96 + s, 0.0, 0.0, 0);
				worldrenderer.addVertexWithUV(8, 96 + s, 0.0, 0.0, 0.0);
				tessellator.draw();
				GL11.glColor4f(1, 1, 1, 1);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
			}
		}
		double sh = this.tileentity.getSlag().getFluidAmount() * 52 / this.tileentity.getSlag().getCapacity();
		if (sh >= 52) {
			sh = 53;
		}
		RenderHelper.renderFluid(ForgeryFluids.Slag, 177, 96, 16, sh);

		this.mc.renderEngine.bindTexture(texture);
		this.drawTexturedModalRect(8, 46, 201, 0, 3, 46);
		this.drawTexturedModalRect(165, 46, 201, 0, 3, 46);
		this.drawTexturedModalRect(177, 46, 201, 0, 3, 46);

		this.drawHovering(mouseX, mouseY);
	}

	private void drawHovering(int mouseX, int mouseY) {
		int s = 0;
		for (FluidTank tank : this.tileentity.tanks.values()) {
			if (tank == null)
				continue;
			if (tank.getFluid() == null)
				continue;
			if (tank.getFluid().getFluid() == null)
				continue;
			Fluid fluid = tank.getFluid().getFluid();
			int h = tank.getFluidAmount() * 52 / TileEntityForgeryFurnace.MAX_TANK + 1;
			if (h >= 53) {
				h = 53;
			}
			s -= h;
			if (this.canRenderInfo(mouseX, mouseY, 8, 96 + s, 160, h - 1)) {
				List list = new ArrayList();
				list.add(fluid.getLocalizedName());
				list.add(tank.getFluidAmount() + "/" + TileEntityForgeryFurnace.MAX_TANK);
				this.drawHoveringText(list, mouseX - this.guiLeft, mouseY - this.guiTop);
			}
		}

		if (this.canRenderInfo(mouseX, mouseY, 177, 43, 16, 53)) {
			List list = new ArrayList();
			list.add("Slag");
			list.add(this.tileentity.getSlag().getFluidAmount() + "/" + this.tileentity.getSlag().getCapacity());
			this.drawHoveringText(list, mouseX - this.guiLeft, mouseY - this.guiTop);
		}

		if (this.canRenderInfo(mouseX, mouseY, 8, 101, 100, 7)) {
			List list = new ArrayList();
			list.add("Heat");
			list.add(this.tileentity.getHeat() + "/" + this.tileentity.getMaxHeat());
			this.drawHoveringText(list, mouseX - this.guiLeft, mouseY - this.guiTop);
		}
	}

	private boolean canRenderInfo(int mouseX, int mouseY, int x, int y, int w, int h) {
		mouseX -= this.guiLeft;
		mouseY -= this.guiTop;
		return x <= mouseX && mouseX <= x + w && y <= mouseY && mouseY <= y + h;
	}
}
