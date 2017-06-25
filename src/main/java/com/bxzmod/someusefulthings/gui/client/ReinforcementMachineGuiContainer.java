package com.bxzmod.someusefulthings.gui.client;

import com.bxzmod.someusefulthings.Info;
import com.bxzmod.someusefulthings.gui.server.ReinforcementMachineContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class ReinforcementMachineGuiContainer extends GuiContainer 
{

	private static final String TEXTURE_PATH = Info.MODID + ":" + "textures/gui/container/ReinforcementMachine.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);
    
    ReinforcementMachineContainer inventory;
    
    int totalWorkTime;
    
    int storageRF = 0;

	public ReinforcementMachineGuiContainer(Container inventorySlotsIn) 
	{
		super(inventorySlotsIn);
		this.xSize = 176;
        this.ySize = 133;
		this.inventory = (ReinforcementMachineContainer) inventorySlotsIn;
		this.totalWorkTime = this.inventory.getTotalWorkTime();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2;
		int offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        
        int workTime = this.inventory.getWorkTime();
		int textureWidth = 1 + (int) Math.ceil(22.0 * workTime / this.totalWorkTime);
		this.drawTexturedModalRect(offsetX + 94, offsetY + 20, 0, 133, textureWidth, 17);

	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		String title = I18n.format("tile.reinforcementMachine.name");
		this.fontRendererObj.drawString(title, (this.xSize - this.fontRendererObj.getStringWidth(title)) / 2, 6, 0x404040);
		this.storageRF = this.inventory.getRF();
		String rf = "RF:" + this.storageRF;
		this.fontRendererObj.drawString(rf, 6, 40, 0x404040);
    }

}
