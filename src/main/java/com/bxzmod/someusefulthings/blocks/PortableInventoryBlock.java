package com.bxzmod.someusefulthings.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PortableInventoryBlock extends BlockContainer {

	public PortableInventoryBlock(Material materialIn) {
		super(materialIn);
		
	}

	public PortableInventoryBlock(Material materialIn, MapColor color) {
		super(materialIn, color);
		
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return null;
	}

}
