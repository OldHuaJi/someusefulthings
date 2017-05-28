package com.bxzmod.someusefulthings.gui;

import com.bxzmod.someusefulthings.Main;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiLoader implements IGuiHandler 
{
	public static final int GUI_NUM = 1;
	
	public GuiLoader(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, this);
    }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO 
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO 
		return null;
	}

}
