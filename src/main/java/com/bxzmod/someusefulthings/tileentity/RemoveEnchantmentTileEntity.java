package com.bxzmod.someusefulthings.tileentity;

import com.bxzmod.someusefulthings.blocks.RemoveEnchantment;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class RemoveEnchantmentTileEntity extends TileEntity implements ITickable
{

	public RemoveEnchantmentTileEntity() 
	{
		// TODO
	}
	
	protected int workTime = 0;

    protected ItemStackHandler iInventory = new ItemStackHandler(4);

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
            T result = (T)iInventory;
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
        	if (!this.worldObj.isRemote)
            {
                ItemStack itemStack = iInventory.extractItem(0, 1, true);
                IBlockState state = this.worldObj.getBlockState(pos);

                if (itemStack != null && iInventory.insertItem(2, itemStack, true) == null)
                {
                    this.worldObj.setBlockState(pos, state.withProperty(RemoveEnchantment.WORK, Boolean.TRUE));

                    int workTotalTime = 200;

                    if (++this.workTime >= workTotalTime)
                    {
                        this.workTime = 0;
                        itemStack = iInventory.extractItem(0, 1, false);
                        iInventory.insertItem(2, itemStack, false);
                        this.markDirty();
                    }
                }
                else
                {
                    this.worldObj.setBlockState(pos, state.withProperty(RemoveEnchantment.WORK, Boolean.FALSE));
                }
            }
        }
    }
    
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

}
