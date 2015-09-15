package se.jereq.testmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import se.jereq.testmod.CreativeTab;
import se.jereq.testmod.init.ModItems;

public class ItemBlasterRifle extends ItemBase {
	public ItemBlasterRifle() {
		super("blasterRifle");
		setCreativeTab(CreativeTab.TEST_TAB);
		maxStackSize = 1;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {

		if (playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(ModItems.blasterAmmo)) {
		}

		return itemStackIn;
	}
}
