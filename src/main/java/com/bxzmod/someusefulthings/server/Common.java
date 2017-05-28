package com.bxzmod.someusefulthings.server;

import com.bxzmod.someusefulthings.blocks.BlockLoader;
import com.bxzmod.someusefulthings.config.ConfigLoader;
import com.bxzmod.someusefulthings.creativetabs.CreativeTabsLoader;
import com.bxzmod.someusefulthings.events.EventLoader;
import com.bxzmod.someusefulthings.gui.GuiLoader;
import com.bxzmod.someusefulthings.items.ItemLoader;
import com.bxzmod.someusefulthings.recipes.CraftingLoader;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Common 
{
	public void preInit(FMLPreInitializationEvent event)
    {
		new ConfigLoader(event);
		new CreativeTabsLoader(event);
		new ItemLoader(event);
		new BlockLoader(event);

    }

    public void init(FMLInitializationEvent event)
    {
    	new CraftingLoader(event);
    	new EventLoader(event);
    	new GuiLoader(event);

    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

}
