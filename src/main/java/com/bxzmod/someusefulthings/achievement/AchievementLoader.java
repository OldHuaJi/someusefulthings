package com.bxzmod.someusefulthings.achievement;

import com.bxzmod.someusefulthings.items.ItemLoader;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class AchievementLoader 
{
	public static Achievement kill_creative = new Achievement("achievement.someusefulthings.infinity", "someusefulthings.infinity", 5, -4, ItemLoader.artifactsword, AchievementList.BUILD_SWORD);

	public AchievementLoader(FMLInitializationEvent event) 
	{
		kill_creative.setSpecial().registerStat();
		
	}

}
