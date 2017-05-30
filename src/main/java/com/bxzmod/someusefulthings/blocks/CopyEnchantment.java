package com.bxzmod.someusefulthings.blocks;

import javax.annotation.Nullable;

import com.bxzmod.someusefulthings.Main;
import com.bxzmod.someusefulthings.creativetabs.CreativeTabsLoader;
import com.bxzmod.someusefulthings.gui.GuiLoader;
import com.bxzmod.someusefulthings.tileentity.CopyEnchantmentTileEntity;
import com.bxzmod.someusefulthings.tileentity.ReinforcementMachineTileEntity;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CopyEnchantment extends BlockContainer 
{

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool WORK = PropertyBool.create("work");

	public CopyEnchantment()
	{
	    super(Material.GRASS);
	    this.setUnlocalizedName("copyEnchantment");
	    this.setRegistryName("copy_enchantment");
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
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, ItemStack stack)
    {
		IBlockState origin = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, stack);
        return origin.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
        	int id = GuiLoader.GUI_C_E;
            playerIn.openGui(Main.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
	
	@Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new CopyEnchantmentTileEntity();
    }
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }


}
