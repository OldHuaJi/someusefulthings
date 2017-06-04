package com.bxzmod.someusefulthings.blocks;

import com.bxzmod.someusefulthings.Info;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRenderLoader 
{
	@SideOnly(Side.CLIENT)
    public BlockRenderLoader(FMLPreInitializationEvent event)
    {
        //registerRender(BlockLoader.removeEnchantment, BlockLoader.removeEnchantmentBlock);
		registerStateMapper(BlockLoader.removeEnchantment, new StateMap.Builder().build());
		registerStateMapper(BlockLoader.copyEnchantment, new StateMap.Builder().build());
		registerStateMapper(BlockLoader.reinforcementMachine, new StateMap.Builder().build());
		
		registerRenderBlockState(BlockLoader.removeEnchantment, BlockLoader.removeEnchantmentBlock, 0, "remove_enchantment");
		registerRenderBlockState(BlockLoader.copyEnchantment, BlockLoader.copyEnchantmentBlock, 0, "copy_enchantment");
		registerRenderBlockState(BlockLoader.reinforcementMachine, BlockLoader.reinforcementMachineBlock, 0, "reinforcement_machine");

    }
	
	@SideOnly(Side.CLIENT)
    private static void registerRender(Block block, ItemBlock item)
    {
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(block.getRegistryName(), "inventory");
        final int DEFAULT_ITEM_SUBTYPE = 0;
        ModelLoader.setCustomModelResourceLocation(item, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
    }
	
	@SideOnly(Side.CLIENT)
    private static void registerRenderBlockState(Block block, ItemBlock item, int meta, String name)
    {
        ResourceLocation location = new ResourceLocation(Info.MODID, name);
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(location, "inventory"));
        //ModelLoader.registerItemVariants(item, location);
    }
	
	@SideOnly(Side.CLIENT)
    private static void registerStateMapper(Block block, IStateMapper mapper)
    {
        ModelLoader.setCustomStateMapper(block, mapper);
    }

}
