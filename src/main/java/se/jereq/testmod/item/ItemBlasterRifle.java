package se.jereq.testmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import se.jereq.testmod.CreativeTab;
import se.jereq.testmod.entity.EntityBlasterBolt;
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
			worldIn.playSoundAtEntity(playerIn, "testmod:launchBlaster", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));

			if (!worldIn.isRemote) {
				worldIn.spawnEntityInWorld(new EntityBlasterBolt(worldIn, playerIn));
			}
		}

		return itemStackIn;
	}
}
