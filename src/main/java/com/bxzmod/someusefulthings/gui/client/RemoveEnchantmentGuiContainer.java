package com.bxzmod.someusefulthings.gui.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RemoveEnchantmentGuiContainer extends GuiContainer 
{

	public RemoveEnchantmentGuiContainer(Container inventorySlotsIn) 
	{
		super(inventorySlotsIn);
		// TODO 
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO 

	}

}
