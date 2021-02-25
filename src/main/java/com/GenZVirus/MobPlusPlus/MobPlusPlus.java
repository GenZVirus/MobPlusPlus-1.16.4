package com.GenZVirus.MobPlusPlus;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.GenZVirus.MobPlusPlus.Client.Config.MobPlusPlusConfig;
import com.GenZVirus.MobPlusPlus.Client.File.XMLFileJava;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("mobplusplus")
@Mod.EventBusSubscriber(modid = MobPlusPlus.MOD_ID, bus = Bus.MOD)
public class MobPlusPlus
{
    // Directly reference a log4j logger.
	@SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger();

	/*
	 * Mod id reference
	 */
	public static final String MOD_ID = "mobplusplus";
	public static MobPlusPlus instance;
    
    public MobPlusPlus() {
    	File folder = new File("config/MobPlusPlus/");
    	if(!folder.exists()) {
    		try {
    			folder.mkdir();
    		} catch (Exception e) {
    			LOGGER.debug("Failed to create config directory");
    		}
    	}
    	ModLoadingContext.get().registerConfig(Type.COMMON, MobPlusPlusConfig.COMMON_SPEC, "MobPlusPlus/Configs.toml");
    	final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::doClientStuff);

		instance = this;
		
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    	XMLFileJava.checkFileAndMake();
    	XMLFileJava.load();
    }

    
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        
    }

}
