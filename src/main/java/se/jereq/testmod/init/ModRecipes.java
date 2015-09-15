package se.jereq.testmod.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
	public static void addRecipes() {

		GameRegistry.addRecipe(new ItemStack(ModBlocks.testBlock),
				"AAA",
				"AAA",
				"AAA",
				'A', ModItems.testItem);

		GameRegistry.addSmelting(ModBlocks.testBlock, new ItemStack(ModItems.testItem, 9), 1000.f);
	}
}
