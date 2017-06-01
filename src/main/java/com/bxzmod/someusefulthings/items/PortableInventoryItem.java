package com.bxzmod.someusefulthings.items;

import com.bxzmod.someusefulthings.Main;
import com.bxzmod.someusefulthings.capability.CapabilityLoader;
import com.bxzmod.someusefulthings.capability.IPortableInventory;
import com.bxzmod.someusefulthings.creativetabs.CreativeTabsLoader;
import com.bxzmod.someusefulthings.gui.GuiLoader;
import com.bxzmod.someusefulthings.network.DataInteraction;
import com.bxzmod.someusefulthings.network.NetworkLoader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PortableInventoryItem extends Item {

	public PortableInventoryItem() 
	{
		super();
		this.setUnlocalizedName("portableInventoryItem");
		this.setRegistryName("portable_inventory_item");
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabsLoader.tabsomeusefulthings);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) 
	{
		if (!worldIn.isRemote)
		{
			if (playerIn.hasCapability(CapabilityLoader.PORTABLE_INVENTORY, null))
            {
            	DataInteraction message = new DataInteraction();
            	
            	IPortableInventory cp = playerIn.getCapability(CapabilityLoader.PORTABLE_INVENTORY, null);
    			IStorage<IPortableInventory> storage = CapabilityLoader.PORTABLE_INVENTORY.getStorage();
    			
    			message.nbt = new NBTTagCompound();
    			message.nbt.setTag("Items", storage.writeNBT(CapabilityLoader.PORTABLE_INVENTORY, cp, null));
    			
    			NetworkLoader.instance.sendTo(message, (EntityPlayerMP) playerIn);
            }
			BlockPos pos = playerIn.getPosition();
            int id = GuiLoader.GUI_P_I;
            playerIn.openGui(Main.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}
	
	

}
