package com.bxzmod.someusefulthings.items.foods;

import java.util.List;

import com.bxzmod.someusefulthings.creativetabs.CreativeTabsLoader;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RedStoneApple extends ItemFood 
{
	private PotionEffect potionId;
    private float potionEffectProbability;

	public RedStoneApple(int amount, float saturation, boolean isWolfFood) 
	{
		super(amount, saturation, isWolfFood);
		this.setAlwaysEdible();
        this.setUnlocalizedName("redStoneApple");
        this.setRegistryName("redstone_apple");
        this.setCreativeTab(CreativeTabsLoader.tabsomeusefulthings);
        this.setPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 6000, 4, true, true), 1.0F);
		
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote && this.potionId != null && worldIn.rand.nextFloat() < this.potionEffectProbability)
        {
            player.addPotionEffect(new PotionEffect(this.potionId));
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 6000, 4, true, true));
            player.addExperience(10);
        }
    }
	
	@Override
	public ItemFood setPotionEffect(PotionEffect p, float Probability)
    {
        this.potionId = p;
        this.potionEffectProbability = Probability;
        return this;
    }

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) 
	{
		tooltip.add(I18n.format("tooltip.redStoneApple", TextFormatting.BLUE));
		tooltip.add(I18n.format("tooltip.redStoneApple_1", TextFormatting.BLUE));
		tooltip.add(I18n.format("tooltip.redStoneApple_2", TextFormatting.RED));
	}

}
