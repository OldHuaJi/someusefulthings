package com.bxzmod.someusefulthings.items.tools;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.bxzmod.someusefulthings.creativetabs.CreativeTabsLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UniversalTool extends ItemTool 
{
	private final Set<Block> effectiveBlocks;
	protected float efficiencyOnProperMaterial;

	public UniversalTool(float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn, Set<Block> effectiveBlocksIn) 
	{
		super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);
		this.setUnlocalizedName("limitlessTool");
		this.setRegistryName("limitless_tool");
		this.setCreativeTab(CreativeTabsLoader.tabsomeusefulthings);
		this.toolMaterial = materialIn;
		this.effectiveBlocks = effectiveBlocksIn;
		this.maxStackSize = 1;
		this.setMaxDamage(materialIn.getMaxUses());
		this.efficiencyOnProperMaterial = materialIn.getEfficiencyOnProperMaterial();
		this.damageVsEntity = attackDamageIn * materialIn.getDamageVsEntity();
		this.attackSpeed = attackSpeedIn;
		this.setHarvestLevel("axe", 99);
		this.setHarvestLevel("shovel", 99);
		this.setHarvestLevel("pickaxe", 99);
		this.setHarvestLevel("hoe", 99);
        this.setHarvestLevel("sword", 99);

	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) 
	{
		if (player.worldObj.isRemote || player.capabilities.isCreativeMode) 
		{
			return false;
		}
		Block block = player.worldObj.getBlockState(pos).getBlock();
		if (block instanceof IShearable) 
		{
			IShearable target = (IShearable) block;
			if (target.isShearable(itemstack, player.worldObj, pos)) 
			{
				List<ItemStack> drops = target.onSheared(itemstack, player.worldObj, pos,
				EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack));
				Random rand = new Random();

				for (ItemStack stack : drops) 
				{
					float f = 0.7F;
					double d = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
					double d1 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
					double d2 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
					EntityItem entityitem = new EntityItem(player.worldObj, (double) pos.getX() + d, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack);
					entityitem.setDefaultPickupDelay();
					player.worldObj.spawnEntityInWorld(entityitem);
				}

				//itemstack.damageItem(1, player);
				this.setDamage(itemstack, 0);
				player.addStat(StatList.getBlockStats(block));
			}
		}
		return false;
	}

	@SuppressWarnings("incomplete-switch")
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack)) 
		{
			return EnumActionResult.FAIL;
		} 
		else 
		{
			int hook = ForgeEventFactory.onHoeUse(stack, playerIn, worldIn, pos);
			if (hook != 0)
				return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up())) 
			{
				if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) 
				{
					this.setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
					return EnumActionResult.SUCCESS;
				}

				if (block == Blocks.DIRT) 
				{
					switch ((BlockDirt.DirtType) iblockstate.getValue(BlockDirt.VARIANT)) 
					{
					case DIRT:
						this.setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
						return EnumActionResult.SUCCESS;
					case COARSE_DIRT:
						this.setBlock(stack, playerIn, worldIn, pos,
								Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
						return EnumActionResult.SUCCESS;
					}
				}
			}

			return EnumActionResult.PASS;
		}
	}

	protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState state) 
	{
		worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

		if (!worldIn.isRemote) 
		{
			worldIn.setBlockState(pos, state, 11);
			//stack.damageItem(1, player);
			this.setDamage(stack, 0);
		}
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) 
	{
		if (entity.worldObj.isRemote) 
		{
			return false;
		}
		if (entity instanceof IShearable) 
		{
			IShearable target = (IShearable) entity;
			BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);
			if (target.isShearable(itemstack, entity.worldObj, pos)) 
			{
				java.util.List<ItemStack> drops = target.onSheared(itemstack, entity.worldObj, pos,
				EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack));

				Random rand = new Random();
				for (ItemStack stack : drops) 
				{
					EntityItem ent = entity.entityDropItem(stack, 1.0F);
					ent.motionY += rand.nextFloat() * 0.05F;
					ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
					ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
				}
				//itemstack.damageItem(1, entity);
				this.setDamage(itemstack, 0);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
		Block block = state.getBlock();
        if (block == Blocks.OBSIDIAN )
        	return 150.0F;
		return 15.0F;
		/*
        Block block = state.getBlock();
        if (block == Blocks.WEB || state.getMaterial() == Material.LEAVES )
        	return 15.0F;
        else
        	for (String type : getToolClasses(stack))
            {
                if (state.getBlock().isToolEffective(type, state))
                    return efficiencyOnProperMaterial;
            }
        return this.effectiveBlocks.contains(state.getBlock()) ? this.efficiencyOnProperMaterial : 10.0F;
        */
    }
	
	@Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker) 
	{
        //itemStack.damageItem(1, attacker);
		this.setDamage(itemStack, 0);
        return true;
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D)
        {
            //stack.damageItem(1, entityLiving);
        	this.setDamage(stack, 0);
        }

        return true;
    }
	
	@Override
	public boolean canHarvestBlock(IBlockState blockIn)
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) 
	{
		tooltip.add(I18n.format("tooltip.limitlessTool", TextFormatting.YELLOW));
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) 
	{
		ItemStack limitlesstoolwithnbt = new ItemStack(itemIn);
		ItemStack limitlesstoolwithnbt1 = new ItemStack(itemIn);
		limitlesstoolwithnbt.addEnchantment(Enchantment.getEnchantmentByID(21), 10);
    	limitlesstoolwithnbt.addEnchantment(Enchantment.getEnchantmentByID(35), 10);
    	limitlesstoolwithnbt1.addEnchantment(Enchantment.getEnchantmentByID(21), 10);
    	limitlesstoolwithnbt1.addEnchantment(Enchantment.getEnchantmentByID(33), 1);
    	subItems.add(limitlesstoolwithnbt);
    	subItems.add(limitlesstoolwithnbt1);
	}
	
	
}
