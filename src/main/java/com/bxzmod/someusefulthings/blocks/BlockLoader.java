package com.bxzmod.someusefulthings.blocks;

import com.bxzmod.someusefulthings.blocks.itemblock.CopyEnchantmentItemBlock;
import com.bxzmod.someusefulthings.blocks.itemblock.ReinforcementMachineItemBlock;
import com.bxzmod.someusefulthings.blocks.itemblock.RemoveEnchantmentItemBlock;
import com.bxzmod.someusefulthings.fluid.BlockFluidWwwwater;
import com.bxzmod.someusefulthings.fluid.FluidLoader;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockLoader 
{
    public static Block removeEnchantment = new RemoveEnchantment();
    public static RemoveEnchantmentItemBlock removeEnchantmentBlock = new RemoveEnchantmentItemBlock(removeEnchantment);
    public static Block copyEnchantment = new CopyEnchantment();
    public static CopyEnchantmentItemBlock copyEnchantmentBlock = new CopyEnchantmentItemBlock(copyEnchantment);
    public static Block reinforcementMachine = new ReinforcementMachine();
    public static ReinforcementMachineItemBlock reinforcementMachineBlock = new ReinforcementMachineItemBlock(reinforcementMachine);
    

    public BlockLoader(FMLPreInitializationEvent event)
    {
        registerBlock(removeEnchantment);
        registerItem(removeEnchantmentBlock, removeEnchantment);
        registerBlock(copyEnchantment);
        registerItem(copyEnchantmentBlock, copyEnchantment);
        registerBlock(reinforcementMachine);
        registerItem(reinforcementMachineBlock, reinforcementMachine);
        
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
