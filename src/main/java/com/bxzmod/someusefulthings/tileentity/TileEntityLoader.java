package com.bxzmod.someusefulthings.tileentity;

import com.bxzmod.someusefulthings.Info;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityLoader {

	public TileEntityLoader(FMLPreInitializationEvent event)
    {
        registerTileEntity(RemoveEnchantmentTileEntity.class, "RemoveEnchantment");
    }

    public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass, Info.MODID + ":" + id);
    }

}
