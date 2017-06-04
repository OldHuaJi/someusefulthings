package com.bxzmod.someusefulthings.command;

import com.bxzmod.someusefulthings.config.ConfigLoader;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandLoader 
{

	public CommandLoader(FMLServerStartingEvent event) 
	{
		if (ConfigLoader.command_easyset)
			event.registerServerCommand(new Easyset());
	}

}
