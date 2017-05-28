package com.bxzmod.someusefulthings.items;

import com.bxzmod.someusefulthings.items.ItemLoader;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRenderLoader {

	public ItemRenderLoader(FMLPreInitializationEvent event) 
	{
		registerRender(ItemLoader.invinciblering);
		registerRender(ItemLoader.limitlesstool);
		registerRender(ItemLoader.redstoneapple);
	}
	
    @SideOnly(Side.CLIENT)
    private static void registerRender(Item item)
    {
        String name = GameData.getItemRegistry().getNameForObject(item).toString();
    	ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(name, "inventory");
        final int DEFAULT_ITEM_SUBTYPE = 0;
        ModelLoader.setCustomModelResourceLocation(item, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
    }
    
}
