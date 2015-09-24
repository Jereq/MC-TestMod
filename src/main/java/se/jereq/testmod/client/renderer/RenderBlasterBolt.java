package se.jereq.testmod.client.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import se.jereq.testmod.entity.EntityBlasterBolt;

import java.util.Random;

public class RenderBlasterBolt extends Render {

	public RenderBlasterBolt(RenderManager renderManager) {
		super(renderManager);
	}

	public void doRender(EntityBlasterBolt entity, double x, double y, double z, float rotation, float partialTicks) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.f);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(770, 1);

		DataWatcher dw = entity.getDataWatcher();
		double startX = dw.getWatchableObjectFloat(16);
		double startY = dw.getWatchableObjectFloat(17);
		double startZ = dw.getWatchableObjectFloat(18);

		float[] color = {1.f, 0.f, 0.f};

		double origX = x - (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks);
		double origY = y - (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks);
		double origZ = z - (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks);

		float alpha = entity.getOpacity(partialTicks);

		for (int layer = 0; layer < 4; ++layer) {
			generateBar(tessellator, origX + startX, origY + startY, origZ + startZ, x, y, z,
					layer * 0.01 + 0.01, color[0], color[1], color[2], alpha);
		}

		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
	}

	private void generateBar(Tessellator tessellator, double startX, double startY, double startZ, double endX, double endY, double endZ, double size, float r, float g, float b, float a) {
		double deltaX = endX - startX;
		double deltaY = endY - startY;
		double deltaZ = endZ - startZ;

		double magX = Math.abs(deltaX);
		double magY = Math.abs(deltaY);
		double magZ = Math.abs(deltaZ);

		final double small = 0.001d;
		if (magX < small && magY < small && magZ < small) {
			return;
		}

		double side1X;
		double side1Y;
		double side1Z;

		double side2X;
		double side2Y;
		double side2Z;

		if (magX < magY && magX < magZ) {
			side1X = 0;
			side1Y = -deltaZ;
			side1Z = deltaY;

			side2X = deltaY * deltaY + deltaZ * deltaZ;
			side2Y = -deltaX * deltaY;
			side2Z = -deltaX * deltaZ;
		} else if (magY < magZ) {
			side1X = deltaZ;
			side1Y = 0;
			side1Z = -deltaX;

			side2X = -deltaY * deltaX;
			side2Y = deltaZ * deltaZ + deltaX * deltaX;
			side2Z = -deltaY * deltaZ;
		} else {
			side1X = -deltaY;
			side1Y = deltaX;
			side1Z = 0;

			side2X = -deltaZ * deltaX;
			side2Y = -deltaZ * deltaY;
			side2Z = deltaX * deltaX + deltaY * deltaY;
		}

		double side1Length = Math.sqrt(side1X * side1X + side1Y * side1Y + side1Z * side1Z);
		side1X /= side1Length;
		side1Y /= side1Length;
		side1Z /= side1Length;

		double side2Length = Math.sqrt(side2X * side2X + side2Y * side2Y + side2Z * side2Z);
		side2X /= side2Length;
		side2Y /= side2Length;
		side2Z /= side2Length;

		double start1X = startX + side1X * size;
		double start2X = startX + side2X * size;
		double start3X = startX - side1X * size;
		double start4X = startX - side2X * size;
		double start1Y = startY + side1Y * size;
		double start2Y = startY + side2Y * size;
		double start3Y = startY - side1Y * size;
		double start4Y = startY - side2Y * size;
		double start1Z = startZ + side1Z * size;
		double start2Z = startZ + side2Z * size;
		double start3Z = startZ - side1Z * size;
		double start4Z = startZ - side2Z * size;

		double end1X = endX + side1X * size;
		double end2X = endX + side2X * size;
		double end3X = endX - side1X * size;
		double end4X = endX - side2X * size;
		double end1Y = endY + side1Y * size;
		double end2Y = endY + side2Y * size;
		double end3Y = endY - side1Y * size;
		double end4Y = endY - side2Y * size;
		double end1Z = endZ + side1Z * size;
		double end2Z = endZ + side2Z * size;
		double end3Z = endZ - side1Z * size;
		double end4Z = endZ - side2Z * size;

		WorldRenderer renderer = tessellator.getWorldRenderer();
		renderer.startDrawing(GL11.GL_TRIANGLE_STRIP);
		renderer.setColorRGBA_F(r, g, b, a);

		renderer.addVertex(end1X, end1Y, end1Z);
		renderer.addVertex(start1X, start1Y, start1Z);
		renderer.addVertex(end2X, end2Y, end2Z);
		renderer.addVertex(start2X, start2Y, start2Z);
		renderer.addVertex(end3X, end3Y, end3Z);
		renderer.addVertex(start3X, start3Y, start3Z);
		renderer.addVertex(end4X, end4Y, end4Z);
		renderer.addVertex(start4X, start4Y, start4Z);
		renderer.addVertex(end1X, end1Y, end1Z);
		renderer.addVertex(start1X, start1Y, start1Z);

		tessellator.draw();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float rotation, float partialTicks) {
		this.doRender((EntityBlasterBolt) entity, x, y, z, rotation, partialTicks);
	}
}
