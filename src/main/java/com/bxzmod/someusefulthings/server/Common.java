package com.bxzmod.someusefulthings.server;

import com.bxzmod.someusefulthings.achievement.AchievementLoader;
import com.bxzmod.someusefulthings.blocks.BlockLoader;
import com.bxzmod.someusefulthings.capability.CapabilityLoader;
import com.bxzmod.someusefulthings.config.ConfigLoader;
import com.bxzmod.someusefulthings.creativetabs.CreativeTabsLoader;
import com.bxzmod.someusefulthings.entity.EntityLoader;
import com.bxzmod.someusefulthings.events.EventLoader;
import com.bxzmod.someusefulthings.fluid.FluidLoader;
import com.bxzmod.someusefulthings.gui.GuiLoader;
import com.bxzmod.someusefulthings.items.ItemLoader;
import com.bxzmod.someusefulthings.network.NetworkLoader;
import com.bxzmod.someusefulthings.oredictionary.OreDictionaryLoader;
import com.bxzmod.someusefulthings.recipes.CraftingLoader;
import com.bxzmod.someusefulthings.throwable.ThrowableLoader;
import com.bxzmod.someusefulthings.tileentity.TileEntityLoader;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Common 
{
	public void preInit(FMLPreInitializationEvent event)
    {
		new ConfigLoader(event);
		new CapabilityLoader(event);
		new CreativeTabsLoader(event);
		new FluidLoader(event);
		new ItemLoader(event);
		new BlockLoader(event);
		new OreDictionaryLoader(event);
		new TileEntityLoader(event);
		new EntityLoader(event);
		new ThrowableLoader(event);
		new NetworkLoader(event);

    }

    public void init(FMLInitializationEvent event)
    {
    	new CraftingLoader(event);
    	new AchievementLoader(event);
    	new EventLoader(event);
    	new GuiLoader(event);

    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

}
