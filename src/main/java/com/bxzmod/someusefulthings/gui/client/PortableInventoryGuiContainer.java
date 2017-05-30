package com.bxzmod.someusefulthings.gui.client;

import com.bxzmod.someusefulthings.Info;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PortableInventoryGuiContainer extends GuiContainer 
{
	private static final String TEXTURE_PATH = Info.MODID + ":" + "textures/gui/container/PortableInventory.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);

	public PortableInventoryGuiContainer(Container inventorySlotsIn) 
	{
		super(inventorySlotsIn);
		this.xSize = 176;
        this.ySize = 222;
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		String title = I18n.format("gui.PortableInventory");
        this.fontRendererObj.drawString(title, 8, 5, 0x404040);
        
    }

}
