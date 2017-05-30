package com.bxzmod.someusefulthings.oredictionary;

import com.bxzmod.someusefulthings.items.ItemLoader;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryLoader {

	public OreDictionaryLoader(FMLPreInitializationEvent event) 
	{
		ItemStack itemStack = new ItemStack(ItemLoader.limitlesstool);
		OreDictionary.registerOre("limitlesstool", itemStack);
	}

}
