package com.GenZVirus.MobPlusPlus.Client.Events;

import com.GenZVirus.MobPlusPlus.MobPlusPlus;
import com.GenZVirus.MobPlusPlus.Client.GUI.Settings;
import com.GenZVirus.MobPlusPlus.Client.GUI.SlideButton;
import com.GenZVirus.MobPlusPlus.Util.KeyboardHelper;

import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseClickedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseReleasedEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = MobPlusPlus.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class SettingsEvents {

	private static int cooldown = 0;
	private static boolean mousePressed = false;

	@SubscribeEvent
	public static void Arrows(DrawScreenEvent.Post event) {
		if (!(event.getGui() instanceof Settings))
			return;
		SlideButton slideButton = null;
		for (Widget button : Settings.instance.getButtons()) {
			if (button instanceof SlideButton)
				if (((SlideButton) button).isSelected) {
					slideButton = (SlideButton) button;
					break;
				}
		}

		if (slideButton == null)
			return;

		// RIGHT

		if (KeyboardHelper.isHoldingRIGHT() && cooldown == 0) {
			slideButton.addX(1);
			cooldown = 3;
		}

		// LEFT

		if (KeyboardHelper.isHoldingLEFT() && cooldown == 0) {
			slideButton.substractX(1);
			cooldown = 3;
		}
		if (mousePressed) {
			slideButton.setX(event.getMouseX() - slideButton.getWidth() / 2);
		}
	}

	@SubscribeEvent
	public static void MousePressed(MouseClickedEvent.Pre event) {
		if (!(event.getGui() instanceof Settings))
			return;
		SlideButton overlay = null;
		for (Widget button : Settings.instance.getButtons()) {
			if (button instanceof SlideButton)
				if (((SlideButton) button).isSelected) {
					overlay = (SlideButton) button;
					break;
				} else {
					int widthIn = button.x;
					int width = button.getWidth();
					int x = (int) event.getMouseX();
					if (x >= widthIn && x < widthIn + width) {
						mousePressed = true;
					}
				}
		}
		if (overlay == null)
			return;
		int widthIn = overlay.x;
		int width = overlay.getWidth();
		int x = (int) event.getMouseX();
		if (x >= widthIn && x < widthIn + width) {
			mousePressed = true;
		}
	}

	@SubscribeEvent
	public static void MouseReleased(MouseReleasedEvent event) {
		if (!(event.getGui() instanceof Settings))
			return;
		mousePressed = false;
		for (Widget button : Settings.instance.getButtons()) {
			if (button instanceof SlideButton)
				((SlideButton) button).isSelected = false;
		}
	}

	@SubscribeEvent
	public static void cooldown(ClientTickEvent event) {
		if (cooldown > 0) {
			cooldown--;
		}
	}

}
