package com.bxzmod.someusefulthings.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;

public class Infinity extends EntityDamageSource 
{
	public Entity damageSourceEntity;

	public Infinity(Entity damageSourceEntityIn) 
	{
		super("infinity", damageSourceEntityIn);
		
	}

    @Override
    public boolean isDifficultyScaled()
    {
        return false;
    }

}
