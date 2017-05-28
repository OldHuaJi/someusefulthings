package com.bxzmod.someusefulthings.items;

import java.util.Set;

import com.bxzmod.someusefulthings.items.foods.RedStoneApple;
import com.bxzmod.someusefulthings.items.tools.UniversalTool;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLoader 
{
	public static final Item.ToolMaterial LIMITLESS = EnumHelper.addToolMaterial("LIMITLESS", 99, 9999, 16.0F, 10.0F, 1000);
	public static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE, Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH);
	
	public static Item invinciblering = new InvincibleRing();
	
	public static UniversalTool limitlesstool = new UniversalTool(10.0F, 10.0F, LIMITLESS, EFFECTIVE_ON);
	
	public static RedStoneApple redstoneapple = new RedStoneApple(20, 1.0F, false);
	
	public ItemLoader(FMLPreInitializationEvent event)
    {
        register(invinciblering);
        registerTool(limitlesstool);
        registerFood(redstoneapple);
    }
	


    private static void register(Item item)
    {
    	GameRegistry.register(item);
    }
    
    private static void registerTool(ItemTool item)
    {
    	GameRegistry.register(item);
    }
    
    private static void registerFood(ItemFood item)
    {
    	GameRegistry.register(item);
    }

}
