package se.jereq.testmod.recipe;

import cofh.thermalexpansion.block.TEBlocks;
import cpw.mods.fml.common.Loader;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import se.jereq.testmod.init.ModItems;
import se.jereq.testmod.item.ItemBlasterRifle;

import java.util.ArrayList;
import java.util.List;

public class AddRechargeableBatteryRecipe implements IRecipe {

	private List<Item> batteryItems = new ArrayList<Item>();

	public AddRechargeableBatteryRecipe() {
		batteryItems.add(Items.redstone);
		batteryItems.add(ModItems.blasterAmmo);

		if (Loader.isModLoaded("ThermalExpansion")) {
			batteryItems.add(Item.getItemFromBlock(TEBlocks.blockCell));
		}
	}

	private boolean isRechargeableBattery(ItemStack itemStack) {
		return batteryItems.contains(itemStack.getItem());
	}

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
			} else if (isRechargeableBattery(ingredient)) {
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
			} else if (isRechargeableBattery(ingredient)) {
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
		NBTTagCompound compound = result.getTagCompound();
		if (compound == null) {
			compound = new NBTTagCompound();
			result.setTagCompound(compound);
		}

		NBTTagCompound nbt;
		if (!compound.hasKey(ItemBlasterRifle.rechargeableTagKey)) {
			nbt = new NBTTagCompound();
			compound.setTag(ItemBlasterRifle.rechargeableTagKey, nbt);
		} else {
			nbt = compound.getCompoundTag(ItemBlasterRifle.rechargeableTagKey);
		}

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
}
