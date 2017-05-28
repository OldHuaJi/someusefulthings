package com.bxzmod.someusefulthings.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigLoader 
{
	private static Configuration config;
	
	private static final Logger LOGGER = LogManager.getLogger();

    public static boolean invincible_ring;

    public ConfigLoader(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        load();
    }

    public static void load()
    {
    	LOGGER.info("Started loading config. ");
        String comment;

        comment = "Enable invincible ring?(default=true)";
        invincible_ring = config.get(Configuration.CATEGORY_GENERAL, "invincible ring", true, comment).getBoolean();

        config.save();
        LOGGER.info("Finished loading config. ");
    }


}
