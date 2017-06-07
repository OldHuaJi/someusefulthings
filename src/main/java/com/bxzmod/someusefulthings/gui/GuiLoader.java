package com.bxzmod.someusefulthings.gui;

import com.bxzmod.someusefulthings.Main;
import com.bxzmod.someusefulthings.gui.client.CopyEnchantmentGuiContainer;
import com.bxzmod.someusefulthings.gui.client.PortableInventoryGuiContainer;
import com.bxzmod.someusefulthings.gui.client.ReinforcementMachineGuiContainer;
import com.bxzmod.someusefulthings.gui.client.RemoveEnchantmentGuiContainer;
import com.bxzmod.someusefulthings.gui.client.ToolSettingGuiContainer;
import com.bxzmod.someusefulthings.gui.server.CopyEnchantmentContainer;
import com.bxzmod.someusefulthings.gui.server.PortableInventoryContainer;
import com.bxzmod.someusefulthings.gui.server.ReinforcementMachineContainer;
import com.bxzmod.someusefulthings.gui.server.RemoveEnchantmentContainer;
import com.bxzmod.someusefulthings.gui.server.ToolSettingContainer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiLoader implements IGuiHandler 
{
	public static final int GUI_R_E = 1;
	public static final int GUI_C_E = 2;
	public static final int GUI_R_M = 3;
	public static final int GUI_P_I = 4;
	public static final int GUI_T_S = 5;
	
	public static final int DATA_R_E = 1;
	public static final int DATA_C_E = 2;
	public static final int DATA_R_M = 3;
	public static final int DATA_P_I = 4;
	
	public GuiLoader(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, this);
    }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		switch (ID)
        {
        case GUI_R_E:
            return new RemoveEnchantmentContainer(player, world.getTileEntity(new BlockPos(x, y, z)));
        case GUI_C_E:
            return new CopyEnchantmentContainer(player, world.getTileEntity(new BlockPos(x, y, z)));
        case GUI_R_M:
            return new ReinforcementMachineContainer(player, world.getTileEntity(new BlockPos(x, y, z)));
        case GUI_P_I:
            return new PortableInventoryContainer(player);
        case GUI_T_S:
        	return new ToolSettingContainer(player);
        default:
            return null;
        }
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		switch (ID)
        {
        case GUI_R_E:
            return new RemoveEnchantmentGuiContainer(new RemoveEnchantmentContainer(player, world.getTileEntity(new BlockPos(x, y, z))));
        case GUI_C_E:
            return new CopyEnchantmentGuiContainer(new CopyEnchantmentContainer(player, world.getTileEntity(new BlockPos(x, y, z))));
        case GUI_R_M:
            return new ReinforcementMachineGuiContainer(new ReinforcementMachineContainer(player, world.getTileEntity(new BlockPos(x, y, z))));
        case GUI_P_I:
            return new PortableInventoryGuiContainer(new PortableInventoryContainer(player));
        case GUI_T_S:
        	return new ToolSettingGuiContainer(new ToolSettingContainer(player));
        default:
            return null;
        }
	}

}
