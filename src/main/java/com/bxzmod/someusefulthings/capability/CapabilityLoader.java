package com.bxzmod.someusefulthings.capability;

import java.util.concurrent.Callable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityLoader 
{
	@CapabilityInject(IPortableInventory.class)
    public static Capability<IPortableInventory> PORTABLE_INVENTORY = null;

	public CapabilityLoader(FMLPreInitializationEvent event) 
	{
		CapabilityManager.INSTANCE.register(IPortableInventory.class, new PortableInventory.Storage(),
				new Callable<PortableInventory.Implementation>() {
					@Override
					public PortableInventory.Implementation call() throws Exception {
						return new PortableInventory.Implementation();
					}
				});
	}

}
