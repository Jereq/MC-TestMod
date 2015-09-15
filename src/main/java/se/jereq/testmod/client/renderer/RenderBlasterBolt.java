package se.jereq.testmod.client.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import se.jereq.testmod.entity.EntityBlasterBolt;

import java.util.Random;

public class RenderBlasterBolt extends Render {

	public RenderBlasterBolt(RenderManager renderManager) {
		super(renderManager);
	}

	public void doRender(EntityBlasterBolt entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(770, 1);
		double[] xOffsets = new double[8];
		double[] zOffsets = new double[8];
		double totX = 0.0D;
		double totZ = 0.0D;
		Random random = new Random(entity.boltVertex);

		float[] color = {1.f, 0.f, 0.f};

		for (int i = 7; i >= 0; --i)
		{
			xOffsets[i] = totX;
			zOffsets[i] = totZ;
			totX += (double)(random.nextInt(11) - 5);
			totZ += (double)(random.nextInt(11) - 5);
		}

		for (int k1 = 0; k1 < 4; ++k1)
		{
			Random random1 = new Random(entity.boltVertex);

			for (int j = 0; j < 3; ++j)
			{
				int k = 7 - j;
				int l = j == 0 ? 0 : k - 2;

				double prevOffsetX = xOffsets[k] - totX;
				double prevOffsetZ = zOffsets[k] - totZ;

				for (int height = k; height >= l; --height)
				{
					double upperOffsetX = prevOffsetX;
					double upperOffsetZ = prevOffsetZ;

					double upperWidth = 0.1D + (double)k1 * 0.2D;
					double lowerWidth = 0.1D + (double)k1 * 0.2D;

					if (j == 0)
					{
						prevOffsetX += (double)(random1.nextInt(11) - 5);
						prevOffsetZ += (double)(random1.nextInt(11) - 5);

						upperWidth *= (double)height * 0.1D + 1.0D;
						lowerWidth *= (double)(height - 1) * 0.1D + 1.0D;
					}
					else
					{
						prevOffsetX += (double)(random1.nextInt(31) - 15);
						prevOffsetZ += (double)(random1.nextInt(31) - 15);
					}

					final int GL_TRIANGLE_STRIP = 5;
					worldrenderer.startDrawing(GL_TRIANGLE_STRIP);
					worldrenderer.setColorRGBA_F(color[0], color[1], color[2], 0.3F);

					for (int edgeNum = 0; edgeNum < 5; ++edgeNum)
					{
						double upperBaseX = x - upperWidth;
						double upperBaseZ = z - upperWidth;

						double lowerBaseX = x - lowerWidth;
						double lowerBaseZ = z - lowerWidth;

						if (edgeNum == 1 || edgeNum == 2)
						{
							upperBaseX += upperWidth * 2.0D;
							lowerBaseX += lowerWidth * 2.0D;
						}

						if (edgeNum == 2 || edgeNum == 3)
						{
							upperBaseZ += upperWidth * 2.0D;
							lowerBaseZ += lowerWidth * 2.0D;
						}

						worldrenderer.addVertex(lowerBaseX + prevOffsetX, y + (double)(height * 16), lowerBaseZ + prevOffsetZ);
						worldrenderer.addVertex(upperBaseX + upperOffsetX, y + (double)((height + 1) * 16), upperBaseZ + upperOffsetZ);
					}

					tessellator.draw();
				}
			}
		}

		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
		this.doRender((EntityBlasterBolt) entity, x, y, z, p_76986_8_, partialTicks);
	}
}
