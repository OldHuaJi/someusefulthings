package com.bxzmod.someusefulthings.hotkey;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class KeyLoader 
{
	public static KeyBinding digrange;
	public static KeyBinding digdepth;

    public KeyLoader(FMLInitializationEvent event)
    {
        KeyLoader.digrange = new KeyBinding("key.toolrange", Keyboard.KEY_COMMA, "key.tool");
        KeyLoader.digdepth = new KeyBinding("key.tooldepth", Keyboard.KEY_PERIOD, "key.tool");

        ClientRegistry.registerKeyBinding(KeyLoader.digrange);
        ClientRegistry.registerKeyBinding(KeyLoader.digdepth);
    }

}
