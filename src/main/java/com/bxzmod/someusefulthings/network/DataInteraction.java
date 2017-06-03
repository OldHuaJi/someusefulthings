package com.bxzmod.someusefulthings.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bxzmod.someusefulthings.capability.CapabilityLoader;
import com.bxzmod.someusefulthings.capability.IPortableInventory;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DataInteraction implements IMessage 
{
	private static final Logger LOG = LogManager.getLogger();
	
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
	
	public static class ToClientHandler implements IMessageHandler<DataInteraction, IMessage>
	{

		@Override
		public IMessage onMessage(DataInteraction message, MessageContext ctx) 
		{
			if (ctx.side == Side.CLIENT)
			{
				final NBTTagCompound nbt = (NBTTagCompound) message.nbt;
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                	@Override
                    public void run()
                	{
                		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                		if (player.hasCapability(CapabilityLoader.PORTABLE_INVENTORY, null))
                		{
                			IPortableInventory cp = player.getCapability(CapabilityLoader.PORTABLE_INVENTORY, null);
                			IStorage<IPortableInventory> storage = CapabilityLoader.PORTABLE_INVENTORY.getStorage();
                			storage.readNBT(CapabilityLoader.PORTABLE_INVENTORY, cp, null, nbt);
                		}
                	}
                	
                });
				
			}
			return null;
		}
	}
	
	public static class ToServerHandler implements IMessageHandler<DataInteraction, IMessage>
	{
		@Override
		public IMessage onMessage(DataInteraction message, MessageContext ctx) 
		{
			final MinecraftServer Server = FMLCommonHandler.instance().getMinecraftServerInstance();
			
			if (ctx.side == Side.SERVER)
			{
				NBTTagCompound nbt_temp = (NBTTagCompound) message.nbt.copy();
				final String name = message.nbt.getString("name");
				nbt_temp.removeTag("name");
				final NBTTagCompound nbt = nbt_temp;
				//LOG.info(nbt.toString());
				Server.addScheduledTask(new Runnable()
                {
                	@Override
                    public void run()
                	{
                		EntityPlayer player = Server.getPlayerList().getPlayerByUsername(name);
                		if (player.hasCapability(CapabilityLoader.PORTABLE_INVENTORY, null))
                		{
                			IPortableInventory cp = player.getCapability(CapabilityLoader.PORTABLE_INVENTORY, null);
                			IStorage<IPortableInventory> storage = CapabilityLoader.PORTABLE_INVENTORY.getStorage();
                			storage.readNBT(CapabilityLoader.PORTABLE_INVENTORY, cp, null, nbt);
                		}
                	}
                	
                });
				
			}
			return null;
		}
	}
	

}
