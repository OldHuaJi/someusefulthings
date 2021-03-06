package com.bxzmod.someusefulthings.client;

import com.bxzmod.someusefulthings.blocks.BlockRenderLoader;
import com.bxzmod.someusefulthings.entity.EntityRenderLoader;
import com.bxzmod.someusefulthings.fluid.FluidRenderLoader;
import com.bxzmod.someusefulthings.hotkey.KeyLoader;
import com.bxzmod.someusefulthings.items.ItemRenderLoader;
import com.bxzmod.someusefulthings.server.Common;
import com.bxzmod.someusefulthings.throwable.ThrowableRenderLoader;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Client extends Common
{
	@Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        new FluidRenderLoader(event);
        new ItemRenderLoader(event);
        new BlockRenderLoader(event);
        new EntityRenderLoader(event);
        new ThrowableRenderLoader(event);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        //new KeyLoader(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }
    
}
