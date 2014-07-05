package net.bymarcin.evenmoreutilities.mods.bigbattery.gui;

import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBattery;
import net.bymarcin.evenmoreutilities.utils.MathUtils;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiControler extends GuiContainer{
	BigBattery battery;
	private ResourceLocation guiTexture = new ResourceLocation(StaticValues.modId, "textures/gui/bb_gui.png");
	public final int xSize = 256;
	public final int ySize = 170;
	public FontRenderer customFontRender;
	public GuiControler(BigBattery battery, Container c) {
		super(c);
		this.battery = battery;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void initGui() {
		super.initGui();
	}
	
	public void keyTyped(char keyChar, int keyID)
	{
		super.keyTyped(keyChar, keyID);
		if (keyID == Keyboard.KEY_ESCAPE || keyID == mc.gameSettings.keyBindInventory.keyCode)
		{
			this.mc.displayGuiScreen((GuiScreen) null);
			this.mc.setIngameFocus();
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int posX = (this.width - xSize) / 2;
		int posY = (this.height - ySize) / 2;
		double percent = ((double)battery.getStorage().getRealEnergyStored())/(double)battery.getStorage().getRealMaxEnergyStored();
		//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(guiTexture);
		drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);
		drawTexturedModalRect(posX+50, posY+14, 0, 175, (int)(percent*141.0D+0.5D), 71);
		GL11.glPushMatrix();
		GL11.glTranslatef(posX+50, posY+115, 0);
		GL11.glScalef(0.75F, 0.75F, 0.75F);
		
		fontRenderer.drawString("Stored Energy: " + MathUtils.addSI(battery.getStorage().getRealEnergyStored() , "RF") + "("+(int)(percent*100D+0.5) +"%)", 0, 0, 0x000);
		fontRenderer.drawString("Max Stored Energy: " + MathUtils.addSI(battery.getStorage().getRealMaxEnergyStored() , "RF"), 0, 10, 0x000);
		fontRenderer.drawString("Max (In/Out)/Electrode: " + MathUtils.addSI(battery.getStorage().getMaxReceive() , "RF"), 0, 20, 0x000);
		GL11.glPopMatrix();
	}
}
