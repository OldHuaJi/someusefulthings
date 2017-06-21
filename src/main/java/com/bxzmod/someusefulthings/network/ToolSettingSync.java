package com.bxzmod.someusefulthings.network;

import java.util.Arrays;

import com.bxzmod.someusefulthings.capability.CapabilityLoader;
import com.bxzmod.someusefulthings.capability.IPortableInventory;
import com.bxzmod.someusefulthings.items.ItemLoader;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ToolSettingSync implements IMessage 
{
	public NBTTagCompound nbt;

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		nbt = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeTag(buf, nbt);
	}
	
	public static class ToServerSetting implements IMessageHandler<ToolSettingSync, IMessage>
	{
		@Override
		public IMessage onMessage(ToolSettingSync message, MessageContext ctx) 
		{
			final MinecraftServer Server = FMLCommonHandler.instance().getMinecraftServerInstance();
			
			if (ctx.side == Side.SERVER)
			{
				NBTTagCompound nbt_temp = (NBTTagCompound) message.nbt.copy();
				final String name = message.nbt.getString("name");
				nbt_temp.removeTag("name");
				final NBTTagCompound nbt = nbt_temp;
				Server.addScheduledTask(new Runnable()
                {
                	@Override
                    public void run()
                	{
                		EntityPlayer player = Server.getPlayerList().getPlayerByUsername(name);
                		if (player != null)
                		{
                			if(player.getHeldItemMainhand().getItem() == ItemLoader.limitlesstool)
                				this.setEnch(player);
                		}
                	}
                	
                	public void setEnch(EntityPlayer player)
            		{
            			int flag_0 = -1, flag_1 = -1, flag_2 = -1;
            	        boolean flag = false;
            	        if(player.getHeldItemMainhand().getItem() == ItemLoader.limitlesstool)
            	        {
            	            NBTTagList ench = player.getHeldItemMainhand().getEnchantmentTagList();
            	            if(player.getHeldItemMainhand().getTagCompound().hasKey("RepairCost"))
            	                player.getHeldItemMainhand().getTagCompound().removeTag("RepairCost");
            	            if(ench.hasNoTags())
            	            {
            	                player.getHeldItemMainhand().addEnchantment(Enchantments.LOOTING, 10);
            	                player.getHeldItemMainhand().addEnchantment(Enchantments.FORTUNE, 10);
            	            }
            	            else
            	            {
            	                NBTTagList enchtemp = ench.copy();
            	                for (int i = 0; i < enchtemp.tagCount(); i++) 
            	                {
            	                    int m = ((NBTTagCompound) ench.get(i)).getShort("id");
            	                    int n = ((NBTTagCompound) ench.get(i)).getShort("lvl");
            	                    if(m == 21)
            	                        flag_0 = n < 10 ? i : -2;
            	                    if(m == 35)
            	                    {
            	                        flag_1 = i;
            	                        flag = true;
            	                    }
            	                    if(m == 33)
            	                    {
            	                        flag_2 = i;
            	                    }
            	                }
            	                int[] enchlist = {flag_0, flag_1, flag_2};
            	                Arrays.sort(enchlist);
            	                for(int k = 2; k >= 0; k--)
            	                    if(enchlist[k] >= 0)
            	                        ench.removeTag(enchlist[k]);
            	                if(flag_0 > -2)
            	                    player.getHeldItemMainhand().addEnchantment(Enchantments.LOOTING, 10);
            	                if(flag_1 >= 0 && flag)
            	                    player.getHeldItemMainhand().addEnchantment(Enchantments.SILK_TOUCH, 1);
            	                if(flag_2 >= 0 && !flag)
            	                    player.getHeldItemMainhand().addEnchantment(Enchantments.FORTUNE, 10);
            	                if(flag_1 == -1 && flag_2 == -1)
            	                    player.getHeldItemMainhand().addEnchantment(Enchantments.FORTUNE, 10);
            	            }
            	        }
            		}
                	
                });
				
			}
			return null;
		}
		
		
	}

}
