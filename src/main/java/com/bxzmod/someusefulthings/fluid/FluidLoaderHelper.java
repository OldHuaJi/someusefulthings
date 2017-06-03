package com.bxzmod.someusefulthings.fluid;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FluidLoaderHelper 
{
    
    public static BlockFluidWwwwater fluidwwwwater = new BlockFluidWwwwater();
    public static ItemBlock fluidwwwwaterItem = new ItemBlock(fluidwwwwater);
    
    public static ItemBucketWwwwater bucketWwwwater = new ItemBucketWwwwater(fluidwwwwater);

	public FluidLoaderHelper() 
	{
		registerBlock(fluidwwwwater);
        registerItem(fluidwwwwaterItem, fluidwwwwater);
        
        register(bucketWwwwater);
	}
	
	private static void registerBlock(Block block)
    {
        GameRegistry.register(block);
    }
    
    private static void registerItem(ItemBlock item, Block block)
    {
    	item.setRegistryName(block.getRegistryName());
        GameRegistry.register(item);
    }
    
    private static void register(Item item)
    {
    	GameRegistry.register(item);
    }
    
    

}
