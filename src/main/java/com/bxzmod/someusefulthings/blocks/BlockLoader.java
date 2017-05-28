package com.bxzmod.someusefulthings.blocks;

import com.bxzmod.someusefulthings.blocks.itemblock.RemoveEnchantmentItemBlock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockLoader 
{
    public static Block removeEnchantment = new RemoveEnchantment();
    public static RemoveEnchantmentItemBlock removeEnchantmentBlock = new RemoveEnchantmentItemBlock(removeEnchantment);

    public BlockLoader(FMLPreInitializationEvent event)
    {
        registerBlock(removeEnchantment);
        registerItem(removeEnchantmentBlock, removeEnchantment);
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
}
