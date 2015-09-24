package se.jereq.testmod.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import se.jereq.testmod.init.ModItems;
import se.jereq.testmod.item.ItemBlasterRifle;

public class RemoveRechargeableBatteryRecipe implements IRecipe {
	@Override
	public boolean matches(InventoryCrafting inventory, World worldIn) {
		int slots = inventory.getSizeInventory();
		boolean foundRechargeableRifle = false;

		for (int i = 0; i < slots; ++i) {
			ItemStack ingredient = inventory.getStackInSlot(i);
			if (ingredient == null) {
				continue;
			}

			if (ingredient.getItem() == ModItems.blasterRifle) {
				NBTTagCompound nbt = ingredient.getTagCompound();

				if (nbt == null || !nbt.hasKey(ItemBlasterRifle.rechargeableTagKey, Constants.NBT.TAG_COMPOUND)) {
					return false;
				}

				if (foundRechargeableRifle) {
					return false;
				}

				foundRechargeableRifle = true;
			} else {
				return false;
			}
		}

		return foundRechargeableRifle;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		int slots = inventory.getSizeInventory();
		ItemStack foundRechargeableRifle = null;

		for (int i = 0; i < slots; ++i) {
			ItemStack ingredient = inventory.getStackInSlot(i);
			if (ingredient == null) {
				continue;
			}

			if (ingredient.getItem() == ModItems.blasterRifle) {
				NBTTagCompound nbt = ingredient.getTagCompound();

				if (nbt == null || !nbt.hasKey(ItemBlasterRifle.rechargeableTagKey, Constants.NBT.TAG_COMPOUND)) {
					return null;
				}

				if (foundRechargeableRifle != null) {
					return null;
				}

				foundRechargeableRifle = ingredient;
			} else {
				return null;
			}
		}

		if (foundRechargeableRifle == null) {
			return null;
		}

		return ItemStack.loadItemStackFromNBT(foundRechargeableRifle.getSubCompound(ItemBlasterRifle.rechargeableTagKey, false));
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inventory) {
		ItemStack[] result = new ItemStack[inventory.getSizeInventory()];

		for (int i = 0; i < result.length; ++i)
		{
			ItemStack itemstack = inventory.getStackInSlot(i);
			if (itemstack != null && itemstack.getItem() == ModItems.blasterRifle) {
				ItemStack nonRechargeableRifle = itemstack.copy();
				nonRechargeableRifle.getTagCompound().removeTag(ItemBlasterRifle.rechargeableTagKey);
				result[i] = nonRechargeableRifle;
			} else {
				result[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
			}
		}

		return result;
	}
}
