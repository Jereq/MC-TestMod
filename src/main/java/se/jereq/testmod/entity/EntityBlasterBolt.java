package se.jereq.testmod.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBlasterBolt extends EntityThrowable {

	public static final String name = "testmod_blasterBolt";

	public long boltVertex;

	public EntityBlasterBolt(World worldIn) {
		super(worldIn);
		boltVertex = this.rand.nextLong();
	}

	public EntityBlasterBolt(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
		boltVertex = this.rand.nextLong();
	}

	public EntityBlasterBolt(World worldIn, double x, double y, double p_i1778_6_) {
		super(worldIn, x, y, p_i1778_6_);
		boltVertex = this.rand.nextLong();
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
		return 5.f;
	}
}
