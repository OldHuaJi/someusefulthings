package com.bxzmod.someusefulthings.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotItemHandlerHelper extends SlotItemHandler 
{
	private final int num;

	public SlotItemHandlerHelper(IItemHandler itemHandler, int index, int xPosition, int yPosition) 
	{
		super(itemHandler, index, xPosition, yPosition);
		this.num = index;
	}

	@Override
	public boolean canTakeStack(EntityPlayer playerIn) 
	{
		return this.getItemHandler().getStackInSlot(num)!=null && this.getItemHandler().getStackInSlot(num).stackSize > 0;
	}

}
