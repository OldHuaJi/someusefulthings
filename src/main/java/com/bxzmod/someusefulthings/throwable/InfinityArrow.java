package com.bxzmod.someusefulthings.throwable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class InfinityArrow extends EntitySnowball 
{
	private int ticksInGround;
	private EntityLivingBase shootingEntity;

	public InfinityArrow(World worldIn, double x, double y, double z) 
	{
		super(worldIn, x, y, z);
		
	}

	public InfinityArrow(World worldIn, EntityLivingBase shooter) 
	{
		super(worldIn, shooter);
		this.shootingEntity = shooter;
		
	}

	public InfinityArrow(World worldIn) 
	{
		super(worldIn);
		
	}
	
	public static void registerFixesEgg(DataFixer fixer)
    {
        EntityThrowable.registerFixesThrowable(fixer, "ThrownInfinityArrow");
    }

	@Override
	protected void onImpact(RayTraceResult raytraceResultIn)
	{
		if (!this.worldObj.isRemote)
		{
			if (raytraceResultIn.entityHit != null && raytraceResultIn.entityHit instanceof EntityLivingBase && !(raytraceResultIn.entityHit instanceof EntityPlayer)) 
			{
				EntityLivingBase e = (EntityLivingBase) raytraceResultIn.entityHit;
				if (this.shootingEntity != null && this.shootingEntity instanceof EntityPlayer) 
				{
					EntityPlayer p = (EntityPlayer) this.shootingEntity;
					if (!e.isDead && e.getHealth() > 0) 
					{
						raytraceResultIn.entityHit.attackEntityFrom(new EntityDamageSource("infinity", p), e.getHealth());
						e.setHealth(0);
						e.onDeath(new EntityDamageSource("infinity", p));
					}
				}
			}
			this.setDead();
		}
	}

	@Override
	protected float getGravityVelocity() 
	{
		return 0.00F;
	}
	
}