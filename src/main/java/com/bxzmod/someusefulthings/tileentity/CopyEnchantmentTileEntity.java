package com.bxzmod.someusefulthings.tileentity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bxzmod.someusefulthings.blocks.CopyEnchantment;
import com.bxzmod.someusefulthings.blocks.RemoveEnchantment;
import com.bxzmod.someusefulthings.tileentity.RemoveEnchantmentTileEntity.ItemStackHandlerMe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CopyEnchantmentTileEntity extends TileEntity implements ITickable
{
	private static final int workTotalTime = 200;

	public CopyEnchantmentTileEntity() 
	{
		
	}
	
	protected int workTime = 0;
    
	protected ItemStackHandlerMe iInventory = new ItemStackHandlerMe(4);

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            @SuppressWarnings("unchecked")
            T result = (T) iInventory;
            return result;
        }
        return super.getCapability(capability, facing);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.iInventory.deserializeNBT(compound.getCompoundTag("iInventory"));
        this.workTime = compound.getInteger("WorkTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("iInventory", this.iInventory.serializeNBT());
        compound.setInteger("WorkTime", this.workTime);
        return compound;
    }
    
    @Override
    public void update()
    {
		if (!this.worldObj.isRemote) 
		{
			ItemStack itemStack_0 = iInventory.extractItemMe(0, 1, true);
			ItemStack itemStack_1 = iInventory.extractItemMe(1, 16, true);
			ItemStack itemStack_2 = iInventory.extractItemMe(2, 1, true);
			ItemStack itemStack_3 = new ItemStack(Items.ENCHANTED_BOOK);
			IBlockState state = this.worldObj.getBlockState(pos);

			if (itemStack_0 != null && itemStack_1 != null && itemStack_2 != null && iInventory.insertItemMe(3, itemStack_3, true) == null && itemStack_1.stackSize == 16) {
				if (itemStack_0.getItem().equals(Items.ENCHANTED_BOOK)) {
					this.worldObj.setBlockState(pos, state.withProperty(CopyEnchantment.WORK, Boolean.TRUE));
					if (++this.workTime >= workTotalTime) {
						this.workTime = 0;
						itemStack_3 = itemStack_0.copy();
						iInventory.extractItemMe(1, 16, false);
						iInventory.extractItemMe(2, 1, false);
						iInventory.insertItemMe(3, itemStack_3, false);
						this.markDirty();
					}

				}
			} else {
				this.worldObj.setBlockState(pos, state.withProperty(CopyEnchantment.WORK, Boolean.FALSE));
				this.workTime = 0;
			}
		}
    }
    
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }
    
    public int getWorkTime()
    {
        return this.workTime;
    }
    
    public int getTotalWorkTime()
    {
        return this.workTotalTime;
    }

    public class ItemStackHandlerMe extends ItemStackHandler 
    {
    	ItemStack lapis_stack = new ItemStack(Items.DYE, 1 , 4);
    	
    	public ItemStackHandlerMe() 
    	{
			super();
		}

		public ItemStackHandlerMe(int size) 
		{
			super(size);
		}

		public ItemStackHandlerMe(ItemStack[] stacks) 
		{
			super(stacks);
			
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) 
		{
			if(slot == 0 && !(stack.getItem().equals(Items.ENCHANTED_BOOK)))
				return stack;
			if(slot == 1 && !(stack.getItem().equals(Items.DYE) && lapis_stack.getMetadata() == stack.getMetadata()))
				return stack;
			if(slot == 2 && stack.getItem() != Items.BOOK)
				return stack;
			if(slot == 3)
				return stack;
			return super.insertItem(slot, stack, simulate);
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) 
		{
			if(slot == 3)
				return super.extractItem(slot, amount, simulate);
			return null;
		}
		
		public ItemStack insertItemMe(int slot, ItemStack stack, boolean simulate) 
		{
			return super.insertItem(slot, stack, simulate);
		}
		
		public ItemStack extractItemMe(int slot, int amount, boolean simulate) 
		{
			return super.extractItem(slot, amount, simulate);
		}
    }
}
