package se.jereq.testmod.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import se.jereq.testmod.recipe.AddRechargeableBatteryRecipe;
import se.jereq.testmod.recipe.RemoveRechargeableBatteryRecipe;

public class ModRecipes {
	public static void addRecipes() {

		GameRegistry.addRecipe(new ItemStack(ModBlocks.testBlock),
				"AAA",
				"AAA",
				"AAA",
				'A', ModItems.testItem);

		GameRegistry.addSmelting(ModBlocks.testBlock, new ItemStack(ModItems.testItem, 9), 1000.f);

		GameRegistry.addRecipe(new ItemStack(ModItems.blasterAmmo, 12),
				" I ",
				"IRI",
				"IGI",
				'I', Items.iron_ingot,
				'R', Items.redstone,
				'G', Items.gunpowder);

		GameRegistry.addRecipe(new ItemStack(ModItems.blasterRifle),
				"IIF",
				"  I",
				'I', Items.iron_ingot,
				'F', Items.flint_and_steel);

		RecipeSorter.register("testmod:add_rechargeable_battery", AddRechargeableBatteryRecipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		GameRegistry.addRecipe(new AddRechargeableBatteryRecipe());

		RecipeSorter.register("testmod:remove_rechargeable_battery", RemoveRechargeableBatteryRecipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		GameRegistry.addRecipe(new RemoveRechargeableBatteryRecipe());
	}

}
