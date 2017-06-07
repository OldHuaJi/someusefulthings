package com.bxzmod.someusefulthings.fakeplayer;

import java.lang.ref.WeakReference;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class FakePlayerLoader 
{
	private static GameProfile gameProfile;
    private static WeakReference<EntityPlayerMP> fakePlayer;

	public FakePlayerLoader(FMLInitializationEvent event) 
	{
		gameProfile = new GameProfile(UUID.fromString("A4EEC6D7-FA7B-42F9-90CE-4408A2946494"), "[AFAKEPLAYER]");
        fakePlayer = new WeakReference<EntityPlayerMP>(null);
	}

	public static WeakReference<EntityPlayerMP> getFakePlayer(WorldServer server)
    {
        if (fakePlayer.get() == null)
        {
            fakePlayer = new WeakReference<EntityPlayerMP>(FakePlayerFactory.get(server, gameProfile));
        }
        else
        {
            fakePlayer.get().worldObj = server;
        }
        return fakePlayer;
    }
}
