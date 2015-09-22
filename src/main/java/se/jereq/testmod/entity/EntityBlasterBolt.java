package se.jereq.testmod.entity;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlasterBolt extends Entity {

	public static final String name = "testmod_blasterBolt";

	public EntityBlasterBolt(World worldIn) {
		super(worldIn);

		setSize(0.3f, 0.3f);
		ignoreFrustumCheck = true;
	}

	public EntityBlasterBolt(World worldIn, EntityLivingBase throwerIn, Vec3 end) {
		this(worldIn);

		setLocationAndAngles(
				end.xCoord,
				end.yCoord,
				end.zCoord,
				throwerIn.rotationYaw,
				throwerIn.rotationPitch);

		double startX = throwerIn.posX - (double)(MathHelper.cos(rotationYaw / 180.f * (float)Math.PI) * 0.16f);
		double startY = throwerIn.posY + (double) throwerIn.getEyeHeight() - 0.1;
		double startZ = throwerIn.posZ - (double) (MathHelper.sin(rotationYaw / 180.f * (float) Math.PI) * 0.16f);

		DataWatcher dw = getDataWatcher();
		dw.updateObject(16, (float)startX);
		dw.updateObject(17, (float)startY);
		dw.updateObject(18, (float)startZ);
	}

	@Override
	protected void entityInit() {
		DataWatcher dw = getDataWatcher();
		dw.addObject(16, 0.f);
		dw.addObject(17, 0.f);
		dw.addObject(18, 0.f);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		final int lifeTime = 200;
		if (this.ticksExisted > lifeTime) {
			setDead();
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

	@Override
	public boolean writeToNBTOptional(NBTTagCompound tagCompund) {
		return false;
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		double startX = tagCompound.getDouble("startX");
		double startY = tagCompound.getDouble("startY");
		double startZ = tagCompound.getDouble("startZ");

		DataWatcher dw = getDataWatcher();
		dw.updateObject(16, (float) startX);
		dw.updateObject(17, (float) startY);
		dw.updateObject(18, (float) startZ);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		DataWatcher dw = getDataWatcher();
		tagCompound.setDouble("startX", dw.getWatchableObjectFloat(16));
		tagCompound.setDouble("startY", dw.getWatchableObjectFloat(17));
		tagCompound.setDouble("startZ", dw.getWatchableObjectFloat(18));
	}
}
