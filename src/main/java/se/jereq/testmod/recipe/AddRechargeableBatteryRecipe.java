package se.jereq.testmod.recipe;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import se.jereq.testmod.init.ModItems;
import se.jereq.testmod.item.ItemBlasterRifle;

public class AddRechargeableBatteryRecipe implements IRecipe {
	@Override
	public boolean matches(InventoryCrafting inventory, World worldIn) {
		int slots = inventory.getSizeInventory();
		boolean foundNonRechargeableRifle = false;
		boolean foundBattery = false;
		for (int i = 0; i < slots; ++i) {
			ItemStack ingredient = inventory.getStackInSlot(i);
			if (ingredient == null) {
				continue;
			}

			if (ingredient.getItem() == ModItems.blasterRifle) {
				NBTTagCompound nbt = ingredient.getTagCompound();

				if (nbt != null && nbt.hasKey(ItemBlasterRifle.rechargeableTagKey, Constants.NBT.TAG_COMPOUND)) {
					return false;
				}

				if (foundNonRechargeableRifle) {
					return false;
				}

				foundNonRechargeableRifle = true;
			} else if (ingredient.getItem() == ModItems.blasterAmmo || ingredient.getItem() == Items.redstone) {
				if (foundBattery) {
					return false;
				}

				foundBattery = true;
			} else {
				return false;
			}
		}

		return foundBattery && foundNonRechargeableRifle;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		int slots = inventory.getSizeInventory();
		ItemStack foundNonRechargeableRifle = null;
		ItemStack foundBattery = null;
		for (int i = 0; i < slots; ++i) {
			ItemStack ingredient = inventory.getStackInSlot(i);
			if (ingredient == null) {
				continue;
			}

			if (ingredient.getItem() == ModItems.blasterRifle) {
				NBTTagCompound nbt = ingredient.getTagCompound();

				if (nbt != null && nbt.hasKey(ItemBlasterRifle.rechargeableTagKey, Constants.NBT.TAG_COMPOUND)) {
					return null;
				}

				if (foundNonRechargeableRifle != null) {
					return null;
				}

				foundNonRechargeableRifle = ingredient;
			} else if (ingredient.getItem() == ModItems.blasterAmmo || ingredient.getItem() == Items.redstone) {
				if (foundBattery != null) {
					return null;
				}

				foundBattery = ingredient;
			} else {
				return null;
			}
		}

		if (foundBattery == null || foundNonRechargeableRifle == null) {
			return null;
		}

		ItemStack result = foundNonRechargeableRifle.copy();
		NBTTagCompound nbt = result.getSubCompound(ItemBlasterRifle.rechargeableTagKey, true);
		if (foundBattery.stackSize > 1) {
			foundBattery = foundBattery.copy();
			foundBattery.stackSize = 1;
		}
		foundBattery.writeToNBT(nbt);
		return result;
	}

	@Override
	public int getRecipeSize() {
		return 2;
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
			result[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
		}

		return result;
	}
}
