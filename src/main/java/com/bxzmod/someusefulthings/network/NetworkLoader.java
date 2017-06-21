package com.bxzmod.someusefulthings.network;

import com.bxzmod.someusefulthings.Info;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkLoader 
{
	public static SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(Info.MODID);
	
	private static int nextID = 0;

	public NetworkLoader(FMLPreInitializationEvent event) 
	{
		registerMessage(DataInteraction.ToClientHandler.class, DataInteraction.class, Side.CLIENT);
		registerMessage(DataInteraction.ToServerHandler.class, DataInteraction.class, Side.SERVER);
		registerMessage(ToolSettingSync.ToServerSetting.class, ToolSettingSync.class, Side.SERVER);
		
	}

	private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(
            Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side)
    {
        instance.registerMessage(messageHandler, requestMessageType, nextID++, side);
    }
}
