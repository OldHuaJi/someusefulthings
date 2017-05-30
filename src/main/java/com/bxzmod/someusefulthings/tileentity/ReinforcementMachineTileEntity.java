package com.bxzmod.someusefulthings.tileentity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bxzmod.someusefulthings.blocks.ReinforcementMachine;
import com.bxzmod.someusefulthings.blocks.RemoveEnchantment;

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

public class ReinforcementMachineTileEntity extends TileEntity implements ITickable
{

	private static final Logger LOGGER = LogManager.getLogger();
	private static final int workTotalTime = 200;
	boolean flag = false;

	public ReinforcementMachineTileEntity() 
	{
		
	}
	
	protected int workTime = 0;

    protected ItemStackHandler iInventory = new ItemStackHandler(3);

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
			ItemStack itemStack_0 = iInventory.extractItem(0, 1, true);
			ItemStack itemStack_1 = iInventory.extractItem(1, 1, true);
			ItemStack itemStack_2 = new ItemStack(Items.DIAMOND_BOOTS);
			IBlockState state = this.worldObj.getBlockState(pos);

			if (itemStack_0 != null && itemStack_1 != null && iInventory.insertItem(2, itemStack_2, true) == null) {
				this.worldObj.setBlockState(pos, state.withProperty(ReinforcementMachine.WORK, Boolean.TRUE));
				if (++this.workTime >= workTotalTime) {
					this.workTime = 0;
					if (itemStack_0.hasTagCompound()) {
						if(itemStack_0.getTagCompound().getByte("Unbreakable") != (byte) 1){
						itemStack_0.getTagCompound().setByte("Unbreakable", (byte) 1);
						iInventory.extractItem(1, 1, false);
						itemStack_2 = iInventory.extractItem(0, 1, false);
						iInventory.insertItem(2, itemStack_2, false);
						this.markDirty();
						}

					}else{
						NBTTagCompound nbt = new NBTTagCompound();
						nbt.setByte("Unbreakable", (byte) 1);
						itemStack_0.setTagCompound(nbt);
						iInventory.extractItem(1, 1, false);
						itemStack_2 = iInventory.extractItem(0, 1, false);
						iInventory.insertItem(2, itemStack_2, false);
						this.markDirty();
					}
				}
			} else {
				this.worldObj.setBlockState(pos, state.withProperty(ReinforcementMachine.WORK, Boolean.FALSE));
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

}
