package se.jereq.testmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
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

				double maxDist = 100.f;

				Vec3 start = new Vec3(playerIn.posX, playerIn.posY + (double)playerIn.getEyeHeight(), playerIn.posZ);
				Vec3 dir = playerIn.getLook(1.f);
				Vec3 end = start.addVector(dir.xCoord * maxDist, dir.yCoord * maxDist, dir.zCoord * maxDist);
				MovingObjectPosition hit = worldIn.rayTraceBlocks(start, end, false, false, true);

				if (hit != null) {
					boolean mobGriefing = worldIn.getGameRules().getGameRuleBooleanValue("mobGriefing");
					worldIn.newExplosion(null, hit.hitVec.xCoord, hit.hitVec.yCoord, hit.hitVec.zCoord, 2.f, false, mobGriefing);
				}
			}
		}

		return itemStackIn;
	}
}
