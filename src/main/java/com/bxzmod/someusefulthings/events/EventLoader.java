package com.bxzmod.someusefulthings.events;

import com.bxzmod.someusefulthings.items.ItemLoader;
import baubles.api.BaublesApi;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
    

}
