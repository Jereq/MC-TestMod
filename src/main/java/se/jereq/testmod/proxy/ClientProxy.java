package se.jereq.testmod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import se.jereq.testmod.client.settings.KeyBindings;
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

		ModItems.registerRenders(renderItem);
		ModBlocks.registerRenders(renderItem);
	}
}
