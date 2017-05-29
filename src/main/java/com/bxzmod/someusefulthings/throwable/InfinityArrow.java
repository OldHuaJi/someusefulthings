package com.bxzmod.someusefulthings.throwable;

import com.bxzmod.someusefulthings.damagesource.Infinity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InfinityArrow extends EntityArrow 
{
	private int ticksInGround;

	public InfinityArrow(World worldIn, double x, double y, double z) 
	{
		super(worldIn, x, y, z);
		
	}

	public InfinityArrow(World worldIn, EntityLivingBase shooter) 
	{
		super(worldIn, shooter);
		
	}

	public InfinityArrow(World worldIn) 
	{
		super(worldIn);
		
	}

	@Override
	protected ItemStack getArrowStack() 
	{
		return null;
	}
	
	@Override
	protected void onHit(RayTraceResult raytraceResultIn)
	{
		if( raytraceResultIn.entityHit != null && raytraceResultIn.entityHit instanceof EntityLivingBase)
		{
			EntityLivingBase e = (EntityLivingBase) raytraceResultIn.entityHit;
			if(this.shootingEntity != null && this.shootingEntity instanceof EntityPlayer)
			{
				EntityPlayer p = (EntityPlayer) this.shootingEntity;
				if(!e.isDead && e.getHealth() > 0)
		        {
		        	e.getCombatTracker().trackDamage(new Infinity(p), e.getHealth(), e.getHealth());
		            e.setHealth(0);
		            e.onDeath(new EntityDamageSource("infinity", p));
		        }
			}
		}
		
	}
	
	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
	{
		float f = MathHelper.sqrt_double(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt_double(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.ticksInGround = 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
    {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void setVelocity(double x, double y, double z)
    {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(x * x + z * z);
            this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (180D / Math.PI));
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
	}
	
}