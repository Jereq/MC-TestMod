package se.jereq.testmod.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityBlasterBolt extends EntityThrowable {

	public static final String name = "testmod_blasterBolt";

	public long boltVertex;
	public double startX;
	public double startY;
	public double startZ;

	public EntityBlasterBolt(World worldIn) {
		super(worldIn);
		boltVertex = this.rand.nextLong();
		startX = posX;
		startY = posY;
		startZ = posZ;

		DataWatcher dw = getDataWatcher();
		dw.updateObject(16, (float)startX);
		dw.updateObject(17, (float)startY);
		dw.updateObject(18, (float) startZ);
	}

	public EntityBlasterBolt(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
		boltVertex = this.rand.nextLong();
		startX = posX;
		startY = posY;
		startZ = posZ;

		DataWatcher dw = getDataWatcher();
		dw.updateObject(16, (float)startX);
		dw.updateObject(17, (float)startY);
		dw.updateObject(18, (float)startZ);
	}

	public EntityBlasterBolt(World worldIn, double x, double y, double p_i1778_6_) {
		super(worldIn, x, y, p_i1778_6_);
		boltVertex = this.rand.nextLong();
		startX = posX;
		startY = posY;
		startZ = posZ;

		DataWatcher dw = getDataWatcher();
		dw.updateObject(16, (float)startX);
		dw.updateObject(17, (float)startY);
		dw.updateObject(18, (float)startZ);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		DataWatcher dw = getDataWatcher();
		dw.addObject(16, 0.f);
		dw.addObject(17, 0.f);
		dw.addObject(18, 0.f);
	}

	@Override
	protected void onImpact(MovingObjectPosition hit) {
		if (hit.entityHit != null)
		{
			hit.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 3.f);
		}

		if (!this.worldObj.isRemote)
		{
			boolean mobGriefing = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			this.worldObj.newExplosion(null, hit.hitVec.xCoord, hit.hitVec.yCoord, hit.hitVec.zCoord, 2.f, false, mobGriefing);
			this.setDead();
		}
	}

	@Override
	protected float getGravityVelocity() {
		return 0.f;
	}

	@Override
	protected float getVelocity() {
		return 6.f;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setDouble("startX", startX);
		tagCompound.setDouble("startY", startY);
		tagCompound.setDouble("startZ", startZ);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		super.readEntityFromNBT(tagCompound);
		startX = tagCompound.getDouble("startX");
		startY = tagCompound.getDouble("startY");
		startZ = tagCompound.getDouble("startZ");

		DataWatcher dw = getDataWatcher();
		dw.updateObject(16, (float)startX);
		dw.updateObject(17, (float)startY);
		dw.updateObject(18, (float)startZ);
	}
}
