package com.bxzmod.someusefulthings.throwable;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ThrowableRenderLoader {

	public ThrowableRenderLoader(FMLPreInitializationEvent event) 
	{
		registerThrowableRenders();
	}
	
	@SideOnly(Side.CLIENT)
    public static void registerThrowableRenders()
    {
        
    }

    @SideOnly(Side.CLIENT)
    private static <T extends Entity> void registerEntityRender(Class<T> entityClass, IRenderFactory<? super T> renderFactory)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
    }


}
