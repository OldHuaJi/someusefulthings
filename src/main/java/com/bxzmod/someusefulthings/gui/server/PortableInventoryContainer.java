package com.bxzmod.someusefulthings.gui.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class PortableInventoryContainer extends Container 
{
	private ItemStackHandler items = new ItemStackHandler(54);

	public PortableInventoryContainer(EntityPlayer player) 
	{
		super();
		for (int i = 0; i < 6; ++i)
        {
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new SlotItemHandler(items, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
            
        }

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 198));
        }
    }

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) 
	{
		
		return true;
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return null;
    }

}
