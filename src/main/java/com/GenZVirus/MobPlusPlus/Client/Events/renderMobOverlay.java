package com.GenZVirus.MobPlusPlus.Client.Events;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.GenZVirus.MobPlusPlus.MobPlusPlus;
import com.GenZVirus.MobPlusPlus.Client.Config.MobPlusPlusConfig;
import com.GenZVirus.MobPlusPlus.Client.File.XMLFileJava;
import com.GenZVirus.MobPlusPlus.Client.GUI.Settings;
import com.GenZVirus.MobPlusPlus.Client.Render.OverlayRenderer;
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConfirmOpenLinkScreen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MobPlusPlus.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class renderMobOverlay {

	private static Minecraft mc = Minecraft.getInstance();
	public static List<EntityType<?>> RenderableList = Lists.newArrayList();
	
	/****************************
	 * 
	 * Render health bar event
	 * 
	 ****************************/
	
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void Overlay(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {
		if (event.getEntity().equals(Minecraft.getInstance().player))
			return;
		if(!event.getEntity().isNonBoss()) return;
		if(event.getEntity().getDistance(mc.player) > 20) return;
		if(event.getEntity().isInvisible() || event.getEntity().isInvulnerable()) return;
		if (!RenderableList.contains(event.getEntity().getType()))
			return;
		new OverlayRenderer().render(event.getMatrixStack(), event.getBuffers(), event.getLight(), event.getEntity());
	}

	/****************************
	 * 
	 * Cancel vanilla name rendering
	 * 
	 ****************************/
	
	@SubscribeEvent
	public static void RenderName(RenderNameplateEvent event) {
		event.setResult(Result.DENY);

	}
	
	@SubscribeEvent
	public static void onPlayerLogin(PlayerLoggedInEvent event) {
		OverlayRenderer.RED = (float) Integer.parseInt(XMLFileJava.readElement("RED")) / 255;
		OverlayRenderer.GREEN = (float) Integer.parseInt(XMLFileJava.readElement("GREEN")) / 255;
		OverlayRenderer.BLUE = (float) Integer.parseInt(XMLFileJava.readElement("BLUE")) / 255;
	}

	@SubscribeEvent
	public static void MenuOptions(GuiScreenEvent.InitGuiEvent.Post event) {
		if (!ModList.get().isLoaded("betterux")) {
			if (event.getGui() instanceof OptionsScreen && mc.world != null) {
				event.addWidget(new Button(5, event.getGui().height - 25, 100, 20, new TranslationTextComponent("Mob++ Settings"), (x) -> {
					mc.displayGuiScreen(Settings.instance);
				}));
			}
		} else {
			if (event.getGui() instanceof OptionsScreen && mc.world != null) {
				event.addWidget(new Button(5, event.getGui().height - 50, 100, 20, new TranslationTextComponent("Mob++ Settings"), (x) -> {
					mc.displayGuiScreen(Settings.instance);
				}));
			}
		}
		if (event.getGui() instanceof Settings && mc.world != null) {
			event.addWidget(new Button(4, mc.getMainWindow().getScaledHeight() - 30, 20, 20, new TranslationTextComponent(""), (x) -> {
				mc.displayGuiScreen(new ConfirmOpenLinkScreen(renderMobOverlay::confirmLink, new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.com/invite/ty6gQaD").getValue(), false));

			}));
		}
	}

	public static void confirmLink(boolean p_confirmLink_1_) {
		if (p_confirmLink_1_) {
			try {
				openLink(new URI(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.com/invite/ty6gQaD").getValue()));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		mc.displayGuiScreen((Screen) null);
	}

	private static void openLink(URI p_openLink_1_) {
		Util.getOSType().openURI(p_openLink_1_);
	}
	
	public static void initRenderableList() {
		for (EntityType<?> type : ForgeRegistries.ENTITIES.getValues()) {
			if (!MobPlusPlusConfig.COMMON.list.get().contains(type.getRegistryName().toString())) {
				RenderableList.add(type);
			}
		}
	}
}
