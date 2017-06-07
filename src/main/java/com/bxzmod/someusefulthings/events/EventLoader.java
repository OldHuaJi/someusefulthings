package com.bxzmod.someusefulthings.events;

import com.bxzmod.someusefulthings.Info;
import com.bxzmod.someusefulthings.capability.CapabilityLoader;
import com.bxzmod.someusefulthings.capability.IPortableInventory;
import com.bxzmod.someusefulthings.capability.PortableInventory;
import com.bxzmod.someusefulthings.gui.client.ToolSettingGuiContainer;
import com.bxzmod.someusefulthings.hotkey.KeyLoader;
import com.bxzmod.someusefulthings.items.ItemLoader;
import com.bxzmod.someusefulthings.network.DataInteraction;
import com.bxzmod.someusefulthings.network.NetworkLoader;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventLoader 
{
	Item ring = ItemLoader.invinciblering;

	public EventLoader(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
		
	}
	
    @SubscribeEvent
    public void onGetHurt(LivingHurtEvent event)
    {
    	
        if(!(event.getEntity() instanceof EntityPlayer))
            return;
        else 
        {
        	EntityPlayer player = (EntityPlayer)event.getEntityLiving();
        	IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
        	if( ( baubles.getStackInSlot(1)!=null && baubles.getStackInSlot(1).getItem() == ring ) || ( baubles.getStackInSlot(2)!=null && baubles.getStackInSlot(2).getItem() == ring ))
        		event.setCanceled(true);
        }
        	

    }

    @SubscribeEvent
    public void onAttacked(LivingAttackEvent event) 
    {
    	if(!(event.getEntity() instanceof EntityPlayer))
            return;
    	else 
        {
        	EntityPlayer player = (EntityPlayer)event.getEntityLiving();
        	IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
        	if( ( baubles.getStackInSlot(1)!=null && baubles.getStackInSlot(1).getItem() == ring ) || ( baubles.getStackInSlot(2)!=null && baubles.getStackInSlot(2).getItem() == ring ))
        		event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event)
    {
        if(!(event.getEntityLiving() instanceof EntityPlayer))
        	return;
        else 
        {
        	EntityPlayer player = (EntityPlayer)event.getEntityLiving();
        	IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
        	if( ( baubles.getStackInSlot(1)!=null && baubles.getStackInSlot(1).getItem() == ring ) || ( baubles.getStackInSlot(2)!=null && baubles.getStackInSlot(2).getItem() == ring ))
        	{
        		event.setCanceled(true);
        		player.setHealth(player.getMaxHealth());
        	}
        }
    }
    
    @SubscribeEvent
    public void onAttachCapabilitiesEntity(AttachCapabilitiesEvent.Entity event)
    {
        if (event.getEntity() instanceof EntityPlayer)
        {
        	ICapabilitySerializable<NBTTagCompound> provider = new PortableInventory.ProviderPlayer();
            event.addCapability(new ResourceLocation(Info.MODID + ":" + "portable_inventory"), provider);
        }
    }
    
    @SubscribeEvent
    public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event)
    {
    	Capability<IPortableInventory> capability = CapabilityLoader.PORTABLE_INVENTORY;
        IStorage<IPortableInventory> storage = CapabilityLoader.PORTABLE_INVENTORY.getStorage();
       
        NBTTagCompound bags = event.getOriginal().getCapability(capability, null).serializeNBT();
        event.getEntityPlayer().getCapability(capability, null).deserializeNBT(bags);
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
    	if (!event.getWorld().isRemote && event.getEntity() instanceof EntityPlayer)
    	{
    		EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
            if (player.hasCapability(CapabilityLoader.PORTABLE_INVENTORY, null))
            {
            	DataInteraction message = new DataInteraction();
            	
            	IPortableInventory cp = player.getCapability(CapabilityLoader.PORTABLE_INVENTORY, null);
    			IStorage<IPortableInventory> storage = CapabilityLoader.PORTABLE_INVENTORY.getStorage();
    			
    			message.nbt = new NBTTagCompound();
    			message.nbt = (NBTTagCompound) storage.writeNBT(CapabilityLoader.PORTABLE_INVENTORY, cp, null);
    			
    			NetworkLoader.instance.sendTo(message, player);
            }
    	}
    }

    @SubscribeEvent
    public void onFillBucket(FillBucketEvent event)
    {
        BlockPos blockpos = event.getTarget().getBlockPos();
        IBlockState blockState = event.getWorld().getBlockState(blockpos);
        Fluid fluid = FluidRegistry.lookupFluidForBlock(blockState.getBlock());
        if (fluid != null && new Integer(0).equals(blockState.getValue(BlockFluidBase.LEVEL)))
        {
            FluidStack fluidStack = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
            event.getWorld().setBlockToAir(blockpos);
            event.setFilledBucket(FluidContainerRegistry.fillFluidContainer(fluidStack, event.getEmptyBucket()));
            event.setResult(Result.ALLOW);
        }
    }
    /*
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(GuiScreenEvent.KeyboardInputEvent event)
    {
        if (!(KeyLoader.digrange.isPressed()) && !(KeyLoader.digdepth.isPressed()))
        	return;
        if(!FMLClientHandler.instance().isGUIOpen(ToolSettingGuiContainer.class)) 
    	{
    		if(KeyLoader.digrange.isPressed()) 
    		{
    		}
    	}
    }
    */
}
