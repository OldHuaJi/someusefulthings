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
				final ItemStack tool = ItemStack.loadItemStackFromNBT(nbt_temp);
				Server.addScheduledTask(new Runnable()
                {
                	@Override
                    public void run()
                	{
                		EntityPlayer player = Server.getPlayerList().getPlayerByUsername(name);
                		if (player != null)
                		{
                			if(player.getHeldItemMainhand().getItem() == ItemLoader.limitlesstool)
                				player.setHeldItem(EnumHand.MAIN_HAND, tool);
                		}
                	}
                });	
			}
			return null;
		}
	}
}
