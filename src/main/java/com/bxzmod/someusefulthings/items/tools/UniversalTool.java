package com.bxzmod.someusefulthings.items.tools;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.bxzmod.someusefulthings.Main;
import com.bxzmod.someusefulthings.creativetabs.CreativeTabsLoader;
import com.bxzmod.someusefulthings.fakeplayer.FakePlayerLoader;
import com.bxzmod.someusefulthings.gui.GuiLoader;
import com.bxzmod.someusefulthings.items.ItemLoader;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UniversalTool extends ItemTool 
{
	private final Set<Block> effectiveBlocks;
	protected float efficiencyOnProperMaterial;
	EnumFacing facing = EnumFacing.NORTH;

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
		NBTTagCompound dig = new NBTTagCompound();
		dig.setInteger("dig_range", 1);
		dig.setInteger("dig_depth", 1);
		int side = 0;
		//Get a Facing by it's index (0-5). The order is D-U-N-S-W-E.
		//E=x+, U=y+, S=z+
		if (player.worldObj.isRemote) {
			return false;
		}
		if (player.getName().contains("[") || player.getName().contains("]")) {
			return false;
		}
		if(raytraceFromEntity(player.worldObj, player, true, 10).sideHit == null)
			return false;
		facing = raytraceFromEntity(player.worldObj, player, true, 10).sideHit.getOpposite();
		side = facing.getIndex();
		if (itemstack.getTagCompound().hasKey("dig_parameter")) 
		{
			int a = ((NBTTagCompound) itemstack.getTagCompound().getTag("dig_parameter")).getInteger("dig_range");
			int b = ((NBTTagCompound) itemstack.getTagCompound().getTag("dig_parameter")).getInteger("dig_depth");
			if ((a <= 0 || a > 5) || (b <= 0 || b > 9))
			{
				itemstack.getTagCompound().removeTag("dig_parameter");
				itemstack.getTagCompound().setTag("dig_parameter", dig);
				a = 1;
				b = 1;
			}
			if ((a != 1 || b != 1) && this.isNotSafe(pos, player, a, b, side))
			{
				player.addChatMessage(new TextComponentString(I18n.format("limitlessTool.safePrompt")));
				return false;
			}
			switch (a) 
			{
			case 2:
			case 3:
			case 4:
			case 5:
				this.BreakOtherBlock(itemstack, pos, player, a, b, side);
				break;
			case 1:
				if(b == 1)
					this.HelpBlockStartBreak(itemstack, pos, player);
				else
					this.BreakOtherBlock(itemstack, pos, player, a, b, side);
				break;
			default:
				this.HelpBlockStartBreak(itemstack, pos, player);
			}
		}
		else
		{
			itemstack.getTagCompound().setTag("dig_parameter", dig);
			this.HelpBlockStartBreak(itemstack, pos, player);
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
        if (block.getRegistryName().toString().toLowerCase().matches("(.*)obsidian(.*)"))
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
		NBTTagCompound dig = new NBTTagCompound();
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagList nbttaglist = new NBTTagList();
		NBTTagString lore = new NBTTagString(I18n.format("limitlessTool.toolmsg_0"));
		nbttaglist.appendTag(lore);
		tag.setTag("Lore", nbttaglist);
		dig.setInteger("dig_range", 1);
		dig.setInteger("dig_depth", 1);
		ItemStack limitlesstoolwithnbt = new ItemStack(itemIn);
		ItemStack limitlesstoolwithnbt1 = new ItemStack(itemIn);
		limitlesstoolwithnbt.addEnchantment(Enchantment.getEnchantmentByID(21), 10);
    	limitlesstoolwithnbt.addEnchantment(Enchantment.getEnchantmentByID(35), 10);
    	limitlesstoolwithnbt.getTagCompound().setTag("display", tag);
    	limitlesstoolwithnbt.getTagCompound().setTag("dig_parameter", dig);
    	limitlesstoolwithnbt1.addEnchantment(Enchantment.getEnchantmentByID(21), 10);
    	limitlesstoolwithnbt1.addEnchantment(Enchantment.getEnchantmentByID(33), 1);
    	limitlesstoolwithnbt1.getTagCompound().setTag("display", tag);
    	limitlesstoolwithnbt1.getTagCompound().setTag("dig_parameter", dig);
    	subItems.add(limitlesstoolwithnbt);
    	subItems.add(limitlesstoolwithnbt1);
	}
	
	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) 
	{
		if(itemStackIn.getItem() == ItemLoader.limitlesstool && playerIn.isSneaking())
		{
			BlockPos pos = playerIn.getPosition();
            int id = GuiLoader.GUI_T_S;
            playerIn.openGui(Main.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	public boolean HelpBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
	{
		if(player.capabilities.isCreativeMode)
			return false;
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
				this.setDamage(itemstack, 0);
				player.addStat(StatList.getBlockStats(block));
			}
			return true;
		}
		return false;
	}
	
	public static RayTraceResult raytraceFromEntity(World world, Entity player, boolean par3, double range) 
	{
		float f = 1.0F;
		float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
		double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
		double d1 = player.prevPosY + (player.posY - player.prevPosY) * f;
		if (player instanceof EntityPlayer)
			d1 += ((EntityPlayer) player).eyeHeight;
		double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
		Vec3d vec3 = new Vec3d(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = range;
		Vec3d vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
		return world.rayTraceBlocks(vec3, vec31, par3, true ,false);
	}
	
	public void BreakOtherBlock(ItemStack itemstack, BlockPos pos, EntityPlayer player, int range, int depth, int side)
	{
		BlockPos nextpos = pos;
		int y_range = range == 1? 1 : range * 2 - 2, digrange = depth;
		switch(side)
		{
		case 0:
			for(int x = -range + 1; x < range; x++)
				for(int y = 0; y < digrange; y++)
					for(int z = -range + 1; z < range; z++)
					{
						nextpos = pos.add(x, -y, z);
						if(x == 0 && y == 0 && z==0)
							continue;
						this.SimulatePlayerBreakBlock(itemstack, nextpos, player);
					}
			break;
		case 1:
			for(int x = -range + 1; x < range; x++)
				for(int y = 0; y < digrange; y++)
					for(int z = -range + 1; z < range; z++)
					{
						nextpos = pos.add(x, y, z);
						if(x == 0 && y == 0 && z==0)
							continue;
						this.SimulatePlayerBreakBlock(itemstack, nextpos, player);
					}
			break;
		case 2:
			for(int x = -range + 1; x < range; x++)
				for(int y = range == 1? 0 : -1; y < y_range; y++)
					for(int z = 0; z < digrange; z++)
					{
						nextpos = pos.add(x, y, -z);
						if(x == 0 && y == 0 && z==0)
							continue;
						this.SimulatePlayerBreakBlock(itemstack, nextpos, player);
					}
			break;
		case 3:
			for(int x = -range + 1; x < range; x++)
				for(int y = range == 1? 0 : -1; y < y_range; y++)
					for(int z = 0; z < digrange; z++)
					{
						nextpos = pos.add(x, y, z);
						if(x == 0 && y == 0 && z==0)
							continue;
						this.SimulatePlayerBreakBlock(itemstack, nextpos, player);
					}
			break;
		case 4:
			for(int x = 0; x < digrange; x++)
				for(int y = range == 1? 0 : -1; y < y_range; y++)
					for(int z = -range + 1; z < range; z++)
					{
						nextpos = pos.add(-x, y, z);
						if(x == 0 && y == 0 && z==0)
							continue;
						this.SimulatePlayerBreakBlock(itemstack, nextpos, player);
					}
			break;
		case 5:
			for(int x = 0; x < digrange; x++)
				for(int y = range == 1? 0 : -1; y < y_range; y++)
					for(int z = -range + 1; z < range; z++)
					{
						nextpos = pos.add(x, y, z);
						if(x == 0 && y == 0 && z==0)
							continue;
						this.SimulatePlayerBreakBlock(itemstack, nextpos, player);
					}
			break;
		default:return;
		}
	}
	
	public void SimulatePlayerBreakBlock(ItemStack stack, BlockPos pos, EntityPlayer player)
	{
		World world = player.worldObj;
		if(!world.isBlockLoaded(pos))
			return;

		IBlockState state = world.getBlockState(pos);
		Block blk = state.getBlock();

		if(!world.isRemote && blk != null && !blk.isAir(state, world, pos) && state.getPlayerRelativeBlockHardness(player, world, pos) > 0) 
		{
			if(!blk.canHarvestBlock(player.worldObj, pos, player))
				return;

			int exp = ForgeHooks.onBlockBreakEvent(world, ((EntityPlayerMP) player).interactionManager.getGameType(), (EntityPlayerMP) player, pos);
			if(exp == -1)
				return;

			if(!player.capabilities.isCreativeMode) 
			{
				TileEntity tile = world.getTileEntity(pos);
				IBlockState localState = world.getBlockState(pos);
				blk.onBlockHarvested(world, pos, localState, player);

				if(blk.removedByPlayer(state, world, pos, player, true))
				{
					blk.onBlockDestroyedByPlayer(world, pos, state);
					blk.harvestBlock(world, player, pos, state, tile, stack);
				}
			} 
			else 
				world.setBlockToAir(pos);

			if(exp > 0)
				blk.dropXpOnBlockBreak(world, pos, exp);
		}
	}
	
	public boolean isNotSafe(BlockPos pos, EntityPlayer player, int range, int depth, int side)
	{
		BlockPos nextpos = pos;
		int y_range = range == 1? 1 : range * 2 - 2, digrange = depth;
		switch(side)
		{
		case 0:
			for(int x = -range + 1; x < range; x++)
				for(int y = 0; y < digrange; y++)
					for(int z = -range + 1; z < range; z++)
					{
						nextpos = pos.add(x, -y, z);
						if(x == 0 && y == 0 && z==0)
							continue;
						if (!player.worldObj.isAirBlock(nextpos) && player.worldObj.getTileEntity(nextpos) != null)
							return true;
					}
			break;
		case 1:
			for(int x = -range + 1; x < range; x++)
				for(int y = 0; y < digrange; y++)
					for(int z = -range + 1; z < range; z++)
					{
						nextpos = pos.add(x, y, z);
						if(x == 0 && y == 0 && z==0)
							continue;
						if (!player.worldObj.isAirBlock(nextpos) && player.worldObj.getTileEntity(nextpos) != null)
							return true;
					}
			break;
		case 2:
			for(int x = -range + 1; x < range; x++)
				for(int y = range == 1? 0 : -1; y < y_range; y++)
					for(int z = 0; z < digrange; z++)
					{
						nextpos = pos.add(x, y, -z);
						if(x == 0 && y == 0 && z==0)
							continue;
						if (!player.worldObj.isAirBlock(nextpos) && player.worldObj.getTileEntity(nextpos) != null)
							return true;
					}
			break;
		case 3:
			for(int x = -range + 1; x < range; x++)
				for(int y = range == 1? 0 : -1; y < y_range; y++)
					for(int z = 0; z < digrange; z++)
					{
						nextpos = pos.add(x, y, z);
						if(x == 0 && y == 0 && z==0)
							continue;
						if (!player.worldObj.isAirBlock(nextpos) && player.worldObj.getTileEntity(nextpos) != null)
							return true;
					}
			break;
		case 4:
			for(int x = 0; x < digrange; x++)
				for(int y = range == 1? 0 : -1; y < y_range; y++)
					for(int z = -range + 1; z < range; z++)
					{
						nextpos = pos.add(-x, y, z);
						if(x == 0 && y == 0 && z==0)
							continue;
						if (!player.worldObj.isAirBlock(nextpos) && player.worldObj.getTileEntity(nextpos) != null)
							return true;
					}
			break;
		case 5:
			for(int x = 0; x < digrange; x++)
				for(int y = range == 1? 0 : -1; y < y_range; y++)
					for(int z = -range + 1; z < range; z++)
					{
						nextpos = pos.add(x, y, z);
						if(x == 0 && y == 0 && z==0)
							continue;
						if (!player.worldObj.isAirBlock(nextpos) && player.worldObj.getTileEntity(nextpos) != null)
							return true;
					}
			break;
		default:return false;
		}
		return false;
	}
	
}
