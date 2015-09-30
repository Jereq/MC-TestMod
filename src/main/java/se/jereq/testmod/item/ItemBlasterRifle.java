package se.jereq.testmod.item;

import cofh.thermalexpansion.block.cell.ItemBlockCell;
import cpw.mods.fml.common.Loader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import se.jereq.testmod.CreativeTab;
import se.jereq.testmod.entity.EntityBlasterBolt;
import se.jereq.testmod.init.ModItems;
import se.jereq.testmod.reference.Reference;

import java.util.List;

public class ItemBlasterRifle extends ItemBase {

	public static final String rechargeableTagKey = "RechargeableBattery";
	private static final int energyPerShot = 100;

	public ItemBlasterRifle() {
		super("blasterRifle");
		setCreativeTab(CreativeTab.TEST_TAB);
		setTextureName("testmod:blasterRifle");
		setContainerItem(this);
		maxStackSize = 1;
	}

	public boolean isRechargeable(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(rechargeableTagKey, Constants.NBT.TAG_COMPOUND);
	}

	private boolean useBatteryCharge(ItemStack stack) {
		ItemStack battery = ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag(ItemBlasterRifle.rechargeableTagKey));

		if (Loader.isModLoaded(Reference.TE_MOD_ID) && battery.getItem() instanceof ItemBlockCell) {
			ItemBlockCell itemBlockCell = (ItemBlockCell)battery.getItem();

			int simulatedExtractedEnergy = itemBlockCell.extractEnergy(battery, energyPerShot, true);
			if (simulatedExtractedEnergy == energyPerShot) {
				itemBlockCell.extractEnergy(battery, energyPerShot, false);

				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);

		if (isRechargeable(stack)) {
			tooltip.add(StatCollector.translateToLocal("tooltip.rechargeable"));
			ItemStack battery = ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag(rechargeableTagKey));
			tooltip.addAll(battery.getTooltip(playerIn, advanced));
		} else {
			tooltip.add(StatCollector.translateToLocal("tooltip.requiresBatteries"));
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {

		boolean hadAmmo;
		if (playerIn.capabilities.isCreativeMode) {
			hadAmmo = true;
		} else if (isRechargeable(itemStackIn)) {
			hadAmmo = useBatteryCharge(itemStackIn);
		} else {
			hadAmmo = playerIn.inventory.consumeInventoryItem(ModItems.blasterAmmo);
		}

		if (hadAmmo) {
			worldIn.playSoundAtEntity(playerIn, "testmod:launchBlaster", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));

			if (!worldIn.isRemote) {
				double maxDist = 128.f;

				Vec3 start = Vec3.createVectorHelper(playerIn.posX, playerIn.posY + (double)playerIn.getEyeHeight(), playerIn.posZ);
				Vec3 dir = playerIn.getLook(1.f);
				Vec3 end = start.addVector(dir.xCoord * maxDist, dir.yCoord * maxDist, dir.zCoord * maxDist);

				Vec3 rayStart = Vec3.createVectorHelper(start.xCoord, start.yCoord, start.zCoord);
				Vec3 rayEnd = Vec3.createVectorHelper(end.xCoord, end.yCoord, end.zCoord);

				MovingObjectPosition hit = worldIn.func_147447_a(rayStart, rayEnd, false, false, true);

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

					AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
							Math.min(currStartX, endX),
							Math.min(currStartY, endY),
							Math.min(currStartZ, endZ),
							Math.max(currStartX, endX),
							Math.max(currStartY, endY),
							Math.max(currStartZ, endZ)
						);
					@SuppressWarnings("unchecked") // Generics stripped by obfuscation
					List<Entity> entities = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, aabb);

					currStartX = endX;
					currStartY = endY;
					currStartZ = endZ;

					double distanceToClosestEntity = Double.POSITIVE_INFINITY;
					MovingObjectPosition closestEntityHit = null;

					for (Entity target : entities)
					{
						if (target.canBeCollidedWith())
						{
							double margin = 0.3;
							AxisAlignedBB targetAABB = target.boundingBox.expand(margin, margin, margin);
							MovingObjectPosition intercept = targetAABB.calculateIntercept(start, end);

							if (intercept != null)
							{
								double distanceToEntity = start.distanceTo(intercept.hitVec);

								if (distanceToEntity < distanceToClosestEntity)
								{
									closestEntityHit = intercept;
									distanceToClosestEntity = distanceToEntity;
								}
							}
						}
					}

					if (closestEntityHit != null)
					{
						hit = closestEntityHit;
						break;
					}
				}

				if (hit != null) {
					end = hit.hitVec;
					boolean mobGriefing = worldIn.getGameRules().getGameRuleBooleanValue("mobGriefing");
					worldIn.newExplosion(null, end.xCoord, end.yCoord, end.zCoord, 2.f, false, mobGriefing);
				}

				worldIn.spawnEntityInWorld(new EntityBlasterBolt(worldIn, playerIn, end));
			}
		}

		return itemStackIn;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(rechargeableTagKey, Constants.NBT.TAG_COMPOUND);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		if (isRechargeable(itemStack)) {
			ItemStack copy = itemStack.copy();
			copy.getTagCompound().removeTag(rechargeableTagKey);
			return copy;
		} else {
			return null;
		}
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_) {
		return false;
	}
}
