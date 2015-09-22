package se.jereq.testmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import se.jereq.testmod.CreativeTab;
import se.jereq.testmod.entity.EntityBlasterBolt;
import se.jereq.testmod.init.ModItems;

import java.util.List;

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

				Vec3 rayStart = new Vec3(start.xCoord, start.yCoord, start.zCoord);
				Vec3 rayEnd = new Vec3(end.xCoord, end.yCoord, end.zCoord);

				MovingObjectPosition hit = worldIn.rayTraceBlocks(rayStart, rayEnd, false, false, true);

				if (hit != null) {
					end = hit.hitVec;
				}

				double dX = end.xCoord - start.xCoord;
				double dY = end.yCoord - start.yCoord;
				double dZ = end.zCoord - start.zCoord;

				double sideX = Math.abs(dX);
				double sideY = Math.abs(dY);
				double sideZ = Math.abs(dZ);

				double largestSide = Math.max(sideX, Math.max(sideY, sideZ));

				final double maxSideLength = 16.0;
				double numSplits = Math.ceil(largestSide / maxSideLength);

				double currStartX = start.xCoord;
				double currStartY = start.yCoord;
				double currStartZ = start.zCoord;
				double stepX = dX / numSplits;
				double stepY = dY / numSplits;
				double stepZ = dZ / numSplits;

				for (int i = 0; i < numSplits; ++i) {
					double endX = currStartX + stepX;
					double endY = currStartY + stepY;
					double endZ = currStartZ + stepZ;

					AxisAlignedBB aabb = new AxisAlignedBB(currStartX, currStartY, currStartZ, endX, endY, endZ);
					@SuppressWarnings("unchecked") // Generics stripped by obfuscation
					List<Entity> entities = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, aabb);

					currStartX = endX;
					currStartY = endY;
					currStartZ = endZ;

					double distanceToClosestEntity = Double.POSITIVE_INFINITY;
					Entity closestEntity = null;

					for (Entity target : entities)
					{
						if (target.canBeCollidedWith())
						{
							double margin = 0.3;
							AxisAlignedBB targetAABB = target.getEntityBoundingBox().expand(margin, margin, margin);
							MovingObjectPosition intercept = targetAABB.calculateIntercept(start, end);

							if (intercept != null)
							{
								double distanceToEntity = start.distanceTo(intercept.hitVec);

								if (distanceToEntity < distanceToClosestEntity)
								{
									closestEntity = target;
									distanceToClosestEntity = distanceToEntity;
								}
							}
						}
					}

					if (closestEntity != null)
					{
						hit = new MovingObjectPosition(closestEntity);
						break;
					}
				}

				if (hit != null) {
					boolean mobGriefing = worldIn.getGameRules().getGameRuleBooleanValue("mobGriefing");
					worldIn.newExplosion(null, hit.hitVec.xCoord, hit.hitVec.yCoord, hit.hitVec.zCoord, 2.f, false, mobGriefing);
				}
			}
		}

		return itemStackIn;
	}
}
