package se.jereq.testmod.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
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

		ModItems.registerRenders();

		RenderingRegistry.registerEntityRenderingHandler(EntityBlasterBolt.class, new RenderBlasterBolt());
	}
}
