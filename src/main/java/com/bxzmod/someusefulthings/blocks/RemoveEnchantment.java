package com.bxzmod.someusefulthings.blocks;

import java.util.List;

import com.bxzmod.someusefulthings.creativetabs.CreativeTabsLoader;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RemoveEnchantment extends Block 
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool WORK = PropertyBool.create("work");
	
	public RemoveEnchantment()
	{
	    super(Material.GRASS);
	    this.setUnlocalizedName("removeEnchantment");
	    this.setRegistryName("remove_enchantment");
	    this.setHardness(2.0F);
	    this.setHarvestLevel("pickaxe", 0);
	    this.setCreativeTab(CreativeTabsLoader.tabsomeusefulthings);
	    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(WORK, Boolean.FALSE));
	}

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, WORK});
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		int a;
		a = (meta >= 8) ? meta - 8 : meta;
		EnumFacing facing = EnumFacing.getHorizontal(a & 3);
		Boolean work = Boolean.valueOf((a & 4) != 0);
		return this.getDefaultState().withProperty(FACING, facing).withProperty(WORK, work);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
    {
        int facing = state.getValue(FACING).getHorizontalIndex();
        int work = state.getValue(WORK).booleanValue() ? 4 : 0;
        return facing | work;
    }
	
	@Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState origin = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return origin.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        list.add(new ItemStack(itemIn, 1, 0));
    }
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
	    return state;
	}

}
