package se.jereq.testmod.client.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import se.jereq.testmod.client.settings.KeyBindings;
import se.jereq.testmod.init.ModBlocks;
import se.jereq.testmod.reference.Key;

public class KeyInputEventHandler {

	private static Key getPressedKeyBinding() {
		if (KeyBindings.ask.isPressed()) {
			return Key.ASK;
		}

		return Key.UNKNOWN;
	}

	@SubscribeEvent
	public void onKeyInputEvent(InputEvent.KeyInputEvent event) {
		switch (getPressedKeyBinding()) {
			case ASK:
				Minecraft.getMinecraft().thePlayer.inventory.addItemStackToInventory(new ItemStack(ModBlocks.testBlock, 42));
				break;
		}
	}
}
