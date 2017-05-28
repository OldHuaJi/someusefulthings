package com.bxzmod.someusefulthings.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CreativeTabsLoader 
{
	public static CreativeTabsSomeUsefulThings tabsomeusefulthings;
	
    public CreativeTabsLoader(FMLPreInitializationEvent event)
    {
    	tabsomeusefulthings = new CreativeTabsSomeUsefulThings("someusefulthings");
    }

}
