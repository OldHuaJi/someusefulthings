package com.bxzmod.someusefulthings.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bxzmod.someusefulthings.creativetabs.CreativeTabsLoader;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;

public class InvincibleRing extends Item implements IBauble 
{
	//private static final Logger LOGGER = LogManager.getLogger();

	public InvincibleRing() 
	{
		super();
		MinecraftForge.EVENT_BUS.register(this);
		this.setUnlocalizedName("invinciblering");
		this.setRegistryName("invincible_ring");
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabsLoader.tabsomeusefulthings);

	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}

	@Override
	public void onWornTick(ItemStack itemStack, EntityLivingBase e) 
	{
		EntityPlayer player = (EntityPlayer) e;
		if(player.isBurning())
            player.extinguish();
		if (itemStack.getItemDamage() == 0 && player.ticksExisted % 20 == 0) 
		{
			player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 1200, 0, true, true));
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 3, true, true));
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1200, 5, true, true));
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1200, 3, true, true));
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1200, 0, true, true));
			player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 1200, 0, true, true));
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 1200, 0, true, true));
			player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 1200, 0, true, true));
		}
		player.capabilities.allowFlying = true;
		Collection<PotionEffect> effects = player.getActivePotionEffects();
		// LOGGER.info(effects.size());
		if(effects.size() > 0)
		{
            ArrayList<PotionEffect> bad = new ArrayList<PotionEffect>();
            for(Object effect : effects)
            {
                if(effect instanceof PotionEffect)
                {
                    PotionEffect potion = (PotionEffect)effect;
                    if(potion.getPotion().isBadEffect())
                        bad.add(potion);
                }
            }
            if(bad.size() > 0)
            {
                for(PotionEffect potion : bad)
                {
                    player.removePotionEffect(potion.getPotion());
                }
            }
        }
	}
	
	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) 
	{
		((EntityPlayer)player).capabilities.allowFlying = true;
		player.setAir(300);
		player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 1200, 0, true, true));
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 3, true, true));
		player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1200, 5, true, true));
		player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1200, 3, true, true));
		player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1200, 0, true, true));
		player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 1200, 0, true, true));
		player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 1200, 0, true, true));
		player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 1200, 0, true, true));
	}
	
	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) 
	{
		((EntityPlayer)player).capabilities.allowFlying = false;
		player.removePotionEffect(MobEffects.HASTE);
		player.removePotionEffect(MobEffects.SPEED);
		player.removePotionEffect(MobEffects.REGENERATION);
		player.removePotionEffect(MobEffects.RESISTANCE);
		player.removePotionEffect(MobEffects.FIRE_RESISTANCE);
		player.removePotionEffect(MobEffects.WATER_BREATHING);
		player.removePotionEffect(MobEffects.NIGHT_VISION);
		player.removePotionEffect(MobEffects.SATURATION);
		
	}

}