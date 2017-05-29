package com.bxzmod.someusefulthings.recipes;

import com.bxzmod.someusefulthings.config.ConfigLoader;
import com.bxzmod.someusefulthings.items.ItemLoader;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingLoader
{
	public final static ItemStack limitlesstoolwithnbt = new ItemStack(ItemLoader.limitlesstool);
	public final static ItemStack limitlesstoolwithnbt1 = new ItemStack(ItemLoader.limitlesstool);
    public CraftingLoader(FMLInitializationEvent event)
    {
    	limitlesstoolwithnbt.addEnchantment(Enchantment.getEnchantmentByID(21), 10);
    	limitlesstoolwithnbt.addEnchantment(Enchantment.getEnchantmentByID(35), 10);
    	limitlesstoolwithnbt1.addEnchantment(Enchantment.getEnchantmentByID(21), 1);
    	limitlesstoolwithnbt1.addEnchantment(Enchantment.getEnchantmentByID(33), 10);
        registerRecipe();
        registerSmelting();
        registerFuel();
    }

    private static void registerRecipe()
    {
    	if(ConfigLoader.invincible_ring)
    	GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.invinciblering), new Object[]
    	        {
    	                " # ", "#*#", " # ", '#', Items.NETHER_STAR, '*', Item.getItemFromBlock(Blocks.DRAGON_EGG)
    	        });
    	GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.redstoneapple), new Object[]
    	        {
    	                "###", "#*#", "###", '#', Items.REDSTONE, '*', Items.APPLE
    	        });
    	GameRegistry.addShapedRecipe(limitlesstoolwithnbt, new Object[]
    	        {
    	                "PAS", "WDH", " # ", 'P', Items.DIAMOND_PICKAXE, 'A', Items.DIAMOND_AXE, 'S', Items.DIAMOND_SHOVEL, 'W', Items.DIAMOND_SWORD, 'D', Item.getItemFromBlock(Blocks.DIAMOND_BLOCK), 'H', Items.DIAMOND_HOE, '#', Items.SHEARS
    	        });
    	GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.artifactsword), new Object[]
    	        {
    	                "###", "#*#", "###", '#', Items.NETHER_STAR, '*', ItemLoader.compressedDiamondSword
    	        });
    	GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.compressedDiamondSword), new Object[]
    	        {
    	                "###", "###", "###", '#', Items.DIAMOND_SWORD
    	        });
    	GameRegistry.addShapelessRecipe(limitlesstoolwithnbt, limitlesstoolwithnbt1);
    	GameRegistry.addShapelessRecipe(limitlesstoolwithnbt1, limitlesstoolwithnbt);

    }

    private static void registerSmelting()
    {
    	//GameRegistry.addSmelting(BlockLoader.grassBlock, new ItemStack(Items.coal), 0.5F);

    }

    private static void registerFuel()
    {
    	/*GameRegistry.registerFuelHandler(new IFuelHandler()
        {
            @Override
            public int getBurnTime(ItemStack fuel)
            {
                return Items.DIAMOND != fuel.getItem() ? 0 : 12800;
            }
        });*/

    }
}
