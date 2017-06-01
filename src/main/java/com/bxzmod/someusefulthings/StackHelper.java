package com.bxzmod.someusefulthings;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class StackHelper extends ItemStackHandler {

	public StackHelper() 
	{
		this(1);
	}

	public StackHelper(int size) 
	{
		super(size);
	}

	public StackHelper(ItemStack[] stacks) 
	{
		super(stacks);
	}
	
	public ItemStack[] getStackArray()
	{
		return this.stacks;
	}

	public void setStackArray(ItemStack[] s)
	{
		this.stacks = s;
	}
}
