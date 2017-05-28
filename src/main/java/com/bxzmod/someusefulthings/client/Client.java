package com.bxzmod.someusefulthings.client;

import com.bxzmod.someusefulthings.blocks.BlockRenderLoader;
import com.bxzmod.someusefulthings.items.ItemRenderLoader;
import com.bxzmod.someusefulthings.server.Common;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Client extends Common
{
	@Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        new ItemRenderLoader(event);
        new BlockRenderLoader(event);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }
    
}
