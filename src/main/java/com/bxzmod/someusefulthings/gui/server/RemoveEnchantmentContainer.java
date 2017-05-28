package com.bxzmod.someusefulthings.gui.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class RemoveEnchantmentContainer extends Container 
{

	public RemoveEnchantmentContainer() 
	{
		super();
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) 
	{
		// TODO 
		return false;
	}

}
