package com.bxzmod.someusefulthings.recipes;

import com.bxzmod.someusefulthings.blocks.BlockLoader;
import com.bxzmod.someusefulthings.config.ConfigLoader;
import com.bxzmod.someusefulthings.creativetabs.CreativeTabsLoader;
import com.bxzmod.someusefulthings.fluid.FluidLoaderHelper;
import com.bxzmod.someusefulthings.items.ItemLoader;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CraftingLoader
{
	public final static ItemStack limitlesstoolwithnbt = new ItemStack(ItemLoader.limitlesstool);
	public final static ItemStack limitlesstoolwithnbt1 = new ItemStack(ItemLoader.limitlesstool);
    public CraftingLoader(FMLInitializationEvent event)
    {
    	limitlesstoolwithnbt.addEnchantment(Enchantment.getEnchantmentByID(21), 10);
    	limitlesstoolwithnbt.addEnchantment(Enchantment.getEnchantmentByID(35), 10);
    	limitlesstoolwithnbt1.addEnchantment(Enchantment.getEnchantmentByID(21), 10);
    	limitlesstoolwithnbt1.addEnchantment(Enchantment.getEnchantmentByID(33), 1);
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
    	GameRegistry.addShapedRecipe(limitlesstoolwithnbt1, new Object[]
    	        {
    	                "SAP", "WDH", " # ", 'P', Items.DIAMOND_PICKAXE, 'A', Items.DIAMOND_AXE, 'S', Items.DIAMOND_SHOVEL, 'W', Items.DIAMOND_SWORD, 'D', Item.getItemFromBlock(Blocks.DIAMOND_BLOCK), 'H', Items.DIAMOND_HOE, '#', Items.SHEARS
    	        });
    	GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.artifactsword), new Object[]
    	        {
    	                "###", "#*#", "###", '#', Items.NETHER_STAR, '*', ItemLoader.compressedDiamondSword
    	        });
    	GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.compressedDiamondSword), new Object[]
    	        {
    	                "###", "###", "###", '#', Items.DIAMOND_SWORD
    	        });
    	GameRegistry.addRecipe(new ShapedOreRecipe(limitlesstoolwithnbt, new Object[]
    	        {
    	                "#  ", "   ", "   ", '#', "limitlesstool"
    	        }));
    	GameRegistry.addRecipe(new ShapedOreRecipe(limitlesstoolwithnbt1, new Object[]
    	        {
    	                " # ", "   ", "   ", '#', "limitlesstool"
    	        }));
    	GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.removeEnchantmentBlock), new Object[]
    	        {
    	                " # ", "#*#", " # ", '#', Items.DIAMOND, '*', Item.getItemFromBlock(Blocks.ENCHANTING_TABLE)
    	        });
    	GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.reinforcementMachineBlock), new Object[]
    	        {
    	                " # ", "#*#", " # ", '#', Items.NETHER_STAR, '*', Item.getItemFromBlock(Blocks.ENCHANTING_TABLE)
    	        });
    	GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.copyEnchantmentBlock), new Object[]
    	        {
    	                "###", "#*#", "###", '#', Items.ENCHANTED_BOOK, '*', Item.getItemFromBlock(Blocks.ENCHANTING_TABLE)
    	        });
    	for(int i = 0 ; i < 16 ; i++)
    	{
    		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.portableInventoryItem, 1, i), new Object[]
        	        {
        	                "WDW", "LCL", "WDW", 'W', "wool", 
        	                'D', "dye" + toUpperCaseFirstOne(EnumDyeColor.byMetadata(i).toString()),
        	                'C', "chest", 'L', "leather"
        	        }));
    	}
    	
    	GameRegistry.addShapelessRecipe(new ItemStack(FluidLoaderHelper.bucketWwwwater), new ItemStack(Items.WATER_BUCKET), new ItemStack(Items.WATER_BUCKET), new ItemStack(Items.BUCKET));

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
    
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
          return s;
        else
          return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
      }
}
