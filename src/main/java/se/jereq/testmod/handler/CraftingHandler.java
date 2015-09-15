package se.jereq.testmod.handler;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import se.jereq.testmod.init.ModAchievements;
import se.jereq.testmod.init.ModBlocks;
import se.jereq.testmod.util.LogHelper;

public class CraftingHandler {

	@SubscribeEvent
	public void onItemCraftedEvent(ItemCraftedEvent event) {
		if (event.crafting.getItem() == Item.getItemFromBlock(ModBlocks.testBlock)) {
			event.player.triggerAchievement(ModAchievements.test);
		}
	}
}
