package com.bxzmod.someusefulthings.fluid;

import com.bxzmod.someusefulthings.creativetabs.CreativeTabsLoader;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class ItemBucketWwwwater extends ItemBucket 
{

	public ItemBucketWwwwater(Block containedBlockIn) 
	{
		super(containedBlockIn);
		this.setContainerItem(Items.BUCKET);
        this.setUnlocalizedName("bucketWwwwater");
        this.setRegistryName("bucket_wwwwater");
        this.setCreativeTab(CreativeTabsLoader.tabsomeusefulthings);
        FluidContainerRegistry.registerFluidContainer(FluidLoader.fluidWwwwwater, new ItemStack(this), new ItemStack(Items.BUCKET));
	}

}
