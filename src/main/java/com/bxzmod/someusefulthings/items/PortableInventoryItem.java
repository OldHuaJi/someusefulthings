package com.bxzmod.someusefulthings.items;

import com.bxzmod.someusefulthings.Main;
import com.bxzmod.someusefulthings.creativetabs.CreativeTabsLoader;
import com.bxzmod.someusefulthings.gui.GuiLoader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
			BlockPos pos = playerIn.getPosition();
            int id = GuiLoader.GUI_P_I;
            playerIn.openGui(Main.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}
	
	

}
