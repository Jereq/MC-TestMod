package se.jereq.testmod.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.item.Item;
import se.jereq.testmod.init.ModAchievements;
import se.jereq.testmod.init.ModBlocks;

public class CraftingHandler {

	@SubscribeEvent
	public void onItemCraftedEvent(PlayerEvent.ItemCraftedEvent event) {
		if (event.crafting.getItem() == Item.getItemFromBlock(ModBlocks.testBlock)) {
			event.player.triggerAchievement(ModAchievements.test);
		}
	}
}
