package com.bxzmod.someusefulthings.fluid;

import com.bxzmod.someusefulthings.Info;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class FluidLoader 
{
	public static final ResourceLocation still = new ResourceLocation(Info.MODID + ":" + "fluid/wwwwater_still");
    public static final ResourceLocation flowing = new ResourceLocation(Info.MODID + ":" + "fluid/wwwwater_flow");

    public static Fluid fluidWwwwwater = new FluidWwwwater("fluid_wwwwwater", still, flowing);

    public FluidLoader(FMLPreInitializationEvent event)
    {
    	
        if (FluidRegistry.isFluidRegistered(fluidWwwwwater))
        {
            event.getModLog().info("Found fluid {}, the registration is canceled. ", fluidWwwwwater.getName());
            fluidWwwwwater = FluidRegistry.getFluid(fluidWwwwwater.getName());
        }
        else
        {
            FluidRegistry.registerFluid(fluidWwwwwater);
        }
        
        new FluidLoaderHelper();
    }

}
