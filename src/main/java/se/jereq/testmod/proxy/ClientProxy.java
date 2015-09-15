package se.jereq.testmod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import se.jereq.testmod.client.renderer.RenderBlasterBolt;
import se.jereq.testmod.client.settings.KeyBindings;
import se.jereq.testmod.entity.EntityBlasterBolt;
import se.jereq.testmod.init.ModBlocks;
import se.jereq.testmod.init.ModItems;

public final class ClientProxy extends CommonProxy {

	@Override
	public void registerKeyBindings() {
		ClientRegistry.registerKeyBinding(KeyBindings.ask);
	}

	@Override
	public void registerRenders() {
		Minecraft minecraft = Minecraft.getMinecraft();
		RenderItem renderItem = minecraft.getRenderItem();
		RenderManager renderManager = minecraft.getRenderManager();

		ModItems.registerRenders(renderItem);
		ModBlocks.registerRenders(renderItem);

		RenderingRegistry.registerEntityRenderingHandler(EntityBlasterBolt.class, new RenderBlasterBolt(renderManager));
	}
}
