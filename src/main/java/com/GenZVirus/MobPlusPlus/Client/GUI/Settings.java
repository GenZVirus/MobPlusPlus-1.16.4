package com.GenZVirus.MobPlusPlus.Client.GUI;

import java.util.List;

import com.GenZVirus.MobPlusPlus.MobPlusPlus;
import com.GenZVirus.MobPlusPlus.Client.File.XMLFileJava;
import com.GenZVirus.MobPlusPlus.Client.Render.OverlayRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class Settings extends Screen {

	private Button resetButton, save, save_and_close, close, enable_disable_borders;
	private Minecraft mc = Minecraft.getInstance();
	private ResourceLocation background = new ResourceLocation(MobPlusPlus.MOD_ID, "textures/background.png");
	private ResourceLocation DISCORD = new ResourceLocation(MobPlusPlus.MOD_ID, "textures/discord.png");
	private ResourceLocation DISCORD_BACOGROUND = new ResourceLocation(MobPlusPlus.MOD_ID, "textures/discord_background.png");
	public SlideButton RED, GREEN, BLUE;
	private int length = 255;
	private int Width = 318;
	private int height = 166;

	public static Settings instance = new Settings(new TranslationTextComponent("Settings"));

	public Settings(ITextComponent titleIn) {
		super(titleIn);
	}

	@Override
	protected void init() {
		super.init();
		resetButton = new Button(mc.getMainWindow().getScaledWidth() / 2 + Width / 2 - 242, mc.getMainWindow().getScaledHeight() / 2 + height / 2 - 25, 50, 20, new TranslationTextComponent("Reset"), (x) -> {
			XMLFileJava.resetToDefault();
		});

		save = new Button(mc.getMainWindow().getScaledWidth() / 2 + Width / 2 - 187, mc.getMainWindow().getScaledHeight() / 2 + height / 2 - 25, 50, 20, new TranslationTextComponent("Save"), (x) -> {
			XMLFileJava.save();
		});
		
		save_and_close = new Button(mc.getMainWindow().getScaledWidth() / 2 + Width / 2 - 132, mc.getMainWindow().getScaledHeight() / 2 + height / 2 - 25, 70, 20, new TranslationTextComponent("Save&Close"), (x) -> {
			XMLFileJava.save();
			this.closeScreen();
		});
		
		close = new Button(mc.getMainWindow().getScaledWidth() / 2 + Width / 2 - 57, mc.getMainWindow().getScaledHeight() / 2 + height / 2 - 25, 50, 20, new TranslationTextComponent("Close"), (x) -> {
			this.closeScreen();
		});
		
		enable_disable_borders = new Button(mc.getMainWindow().getScaledWidth() / 2 + Width / 2 - 311, mc.getMainWindow().getScaledHeight() / 2 + height / 2 - 25, 65, 20, OverlayRenderer.BORDER ? new TranslationTextComponent("Border On") : new TranslationTextComponent("Border Off"), (x) -> {
			if (OverlayRenderer.BORDER) {
				OverlayRenderer.BORDER = false;
				enable_disable_borders.setMessage(new TranslationTextComponent("Border Off"));
			} else {
				OverlayRenderer.BORDER = true;
				enable_disable_borders.setMessage(new TranslationTextComponent("Border On"));
			}
		});
		
		int offsetRED = 23 + (int) (length * OverlayRenderer.RED);
		int offsetGREEN = 23 + (int) (length * OverlayRenderer.GREEN);
		int offsetBLUE = 23 + (int) (length * OverlayRenderer.BLUE);

		RED = new SlideButton(mc.getMainWindow().getScaledWidth() / 2 - Width / 2 + offsetRED, mc.getMainWindow().getScaledHeight() / 2 + height / 2 - 69, new TranslationTextComponent("RED"), mc.getMainWindow().getScaledWidth() / 2, (x) -> {

		}) {
			@Override
			public void addX(int number) {
				if (length * OverlayRenderer.RED + number <= length) {
					OverlayRenderer.RED = (length * OverlayRenderer.RED + number) / length;
					RED.x = RED.x + number;
				}
			}

			@Override
			public void setX(int X) {
				int value = X - (mc.getMainWindow().getScaledWidth() / 2 - Width / 2 + 23);
				if (value > length)
					value = length;
				if (value < 0)
					value = 0;
				OverlayRenderer.RED = (float) value / length;
				RED.x = mc.getMainWindow().getScaledWidth() / 2 - Width / 2 + 23 + value;

			}

			@Override
			public void substractX(int number) {
				if (length * OverlayRenderer.RED - number >= 0) {
					OverlayRenderer.RED = (length * OverlayRenderer.RED - number) / length;
					RED.x = RED.x - number;
				}
			}
			
			@Override
			public void setDefault(int x) {
				RED.x = mc.getMainWindow().getScaledWidth() / 2 - Width / 2 + 23 + x;
			}
			
		};

		GREEN = new SlideButton(mc.getMainWindow().getScaledWidth() / 2 - Width / 2 + offsetGREEN, mc.getMainWindow().getScaledHeight() / 2 + height / 2 - 53, new TranslationTextComponent("GREEN"), mc.getMainWindow().getScaledWidth() / 2, (x) -> {

		}) {
			@Override
			public void addX(int number) {
				if (length * OverlayRenderer.GREEN + number <= length) {
					OverlayRenderer.GREEN = (length * OverlayRenderer.GREEN + number) / length;
					GREEN.x = GREEN.x + number;
				}
			}

			@Override
			public void setX(int X) {
				int value = X - (mc.getMainWindow().getScaledWidth() / 2 - Width / 2 + 23);
				if (value > length)
					value = length;
				if (value < 0)
					value = 0;
				OverlayRenderer.GREEN = (float) value / length;
				GREEN.x = mc.getMainWindow().getScaledWidth() / 2 - Width / 2 + 23 + value;

			}

			@Override
			public void substractX(int number) {
				if (length * OverlayRenderer.GREEN - number >= 0) {
					OverlayRenderer.GREEN = (length * OverlayRenderer.GREEN - number) / length;
					GREEN.x = GREEN.x - number;
				}
			}
			
			@Override
			public void setDefault(int x) {
				GREEN.x = mc.getMainWindow().getScaledWidth() / 2 - Width / 2 + 23 + x;
			}
		};

		BLUE = new SlideButton(mc.getMainWindow().getScaledWidth() / 2 - Width / 2 + offsetBLUE, mc.getMainWindow().getScaledHeight() / 2 + height / 2 - 37, new TranslationTextComponent("BLUE"), mc.getMainWindow().getScaledWidth() / 2, (x) -> {

		}) {
			@Override
			public void addX(int number) {
				if (length * OverlayRenderer.BLUE + number <= length) {
					OverlayRenderer.BLUE = (length * OverlayRenderer.BLUE + number) / length;
					BLUE.x = BLUE.x + number;
				}
			}

			@Override
			public void setX(int X) {
				int value = X - (mc.getMainWindow().getScaledWidth() / 2 - Width / 2 + 23);
				if (value > length)
					value = length;
				if (value < 0)
					value = 0;
				OverlayRenderer.BLUE = (float) value / length;
				BLUE.x = mc.getMainWindow().getScaledWidth() / 2 - Width / 2 + 23 + value;
			}

			@Override
			public void substractX(int number) {
				if (length * OverlayRenderer.BLUE - number >= 0) {
					OverlayRenderer.BLUE = (length * OverlayRenderer.BLUE - number) / length;
					BLUE.x = BLUE.x - number;
				}
			}
			
			@Override
			public void setDefault(int x) {
				BLUE.x = mc.getMainWindow().getScaledWidth() / 2 - Width / 2 + 23 + x;
			}
		};

		this.addButton(resetButton);
		this.addButton(save);
		this.addButton(save_and_close);
		this.addButton(close);
		this.addButton(enable_disable_borders);
		this.addButton(RED);
		this.addButton(GREEN);
		this.addButton(BLUE);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}

	@Override
	public boolean isPauseScreen() {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
		this.renderBackground(matrixStack);
		mc.getTextureManager().bindTexture(background);
		AbstractGui.blit(new MatrixStack(), mc.getMainWindow().getScaledWidth() / 2 - Width / 2, mc.getMainWindow().getScaledHeight() / 2 - height / 2, 0, 0, 0, Width, height, height, Width);
		mc.getTextureManager().bindTexture(OverlayRenderer.Background);
		AbstractGui.blit(new MatrixStack(), mc.getMainWindow().getScaledWidth() / 2 - 91, mc.getMainWindow().getScaledHeight() / 2 - 40, 0, 0, 0, 182, 16, 16, 182);
		RenderSystem.color4f(OverlayRenderer.RED, OverlayRenderer.GREEN, OverlayRenderer.BLUE, 1.0F);
		mc.getTextureManager().bindTexture(OverlayRenderer.Health);
		AbstractGui.blit(new MatrixStack(), mc.getMainWindow().getScaledWidth() / 2 - 91, mc.getMainWindow().getScaledHeight() / 2 - 40, 0, 0, 0, 182, 16, 16, 182);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		String red = Integer.toString((int) (OverlayRenderer.RED * 255));
		String green = Integer.toString((int) (OverlayRenderer.GREEN * 255));
		String blue = Integer.toString((int) (OverlayRenderer.BLUE * 255));

		mc.fontRenderer.drawString(new MatrixStack(), red, mc.getMainWindow().getScaledWidth() / 2 + Width / 2 - 10 - mc.fontRenderer.getStringWidth(red), mc.getMainWindow().getScaledHeight() / 2 + height / 2 - 68, 0xFFFF0000);
		mc.fontRenderer.drawString(new MatrixStack(), green, mc.getMainWindow().getScaledWidth() / 2 + Width / 2 - 10 - mc.fontRenderer.getStringWidth(green), mc.getMainWindow().getScaledHeight() / 2 + height / 2 - 52, 0xFF00FF00);
		mc.fontRenderer.drawString(new MatrixStack(), blue, mc.getMainWindow().getScaledWidth() / 2 + Width / 2 - 10 - mc.fontRenderer.getStringWidth(blue), mc.getMainWindow().getScaledHeight() / 2 + height / 2 - 36, 0xFF0000FF);

		this.DiscordBackground();
		super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
		this.DiscordIcon();
	}

	/****************************
	 * 
	 * Render Discord Logo and draw "Mob++"
	 * 
	 ****************************/

	@SuppressWarnings("deprecation")
	public void DiscordIcon() {
		mc.getTextureManager().bindTexture(DISCORD);
		RenderSystem.scalef(0.1F, 0.1F, 0.1F);
		RenderSystem.enableBlend();
		int posX = 6;
		int posY = mc.getMainWindow().getScaledHeight() - 28;
		AbstractGui.blit(new MatrixStack(), posX * 10, posY * 10, 0, 0, 0, 160, 160, 160, 160);
		RenderSystem.disableBlend();
		RenderSystem.scalef(10.0F, 10.0F, 10.0F);
		mc.fontRenderer.drawString(new MatrixStack(), "Mob++", posX + 28, posY + 4, 0xFFFFFFFF);
	}

	/****************************
	 * 
	 * Render Discord background
	 * 
	 ****************************/

	public void DiscordBackground() {
		int posX = 0;
		int posY = mc.getMainWindow().getScaledHeight() - 40;
		mc.getTextureManager().bindTexture(DISCORD_BACOGROUND);
		RenderSystem.enableBlend();
		AbstractGui.blit(new MatrixStack(), posX, posY, 0, 0, 0, 120, 40, 40, 120);
		RenderSystem.disableBlend();
	}

	public List<Widget> getButtons() {
		return buttons;
	}

}
