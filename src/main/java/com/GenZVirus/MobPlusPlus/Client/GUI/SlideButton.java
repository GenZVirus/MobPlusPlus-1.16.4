package com.GenZVirus.MobPlusPlus.Client.GUI;

import com.GenZVirus.MobPlusPlus.MobPlusPlus;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SlideButton extends Button{

	private Minecraft mc = Minecraft.getInstance();
	private ResourceLocation slide_button = new ResourceLocation(MobPlusPlus.MOD_ID, "textures/slide_button.png");
	public boolean isSelected = false;
	public int scaledX;
	
	public SlideButton(int widthIn, int heightIn, ITextComponent text, int ScaledX, IPressable onPress) {
		this(widthIn, heightIn, 10, 20, text, onPress);
		this.scaledX = ScaledX;
	}
	
	public SlideButton(int widthIn, int heightIn, int width, int height, ITextComponent text, IPressable onPress) {
		super(widthIn, heightIn, width, height, text, onPress);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		mc.getTextureManager().bindTexture(slide_button);
		AbstractGui.blit(new MatrixStack(), this.x, this.y, 0, 0, 0, 5, 10, 10, 5);
	}
	
	public void onPress() {
		for (Widget button : Settings.instance.getButtons()) {
			if (button instanceof SlideButton) {
				((SlideButton) button).isSelected = false;
			}
		}
		this.isSelected = true;
		super.onPress();
	}
	
	public void addX(int number) {
	}

	public void substractX(int number) {
	}

	public void setX(int X) {
	}
	
	public void setDefault(int x) {
	}
}
