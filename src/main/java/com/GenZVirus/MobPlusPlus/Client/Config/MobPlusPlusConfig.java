package com.GenZVirus.MobPlusPlus.Client.Config;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.GenZVirus.MobPlusPlus.MobPlusPlus;
import com.GenZVirus.MobPlusPlus.Client.Events.renderMobOverlay;
import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = MobPlusPlus.MOD_ID, bus = Bus.MOD)
public abstract class MobPlusPlusConfig {
	
	public static class Client {
		
	}
	
	public static class Common {
		
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> list;
		
		public Common(ForgeConfigSpec.Builder builder) {
			
			builder.push("Blacklist");
			builder.comment("Write between the squared brackets the registry name of the entity you want to blacklist",
					"Format: EntityList = [\"<registry name>\", \"<registry name>\", ... ]",
					"Registry Name format: <namespace>:<path>",
					"Namespace is the name of the mod or minecraft itself",
					"Path is the name of the mob as the developer codded it to be",
					"Example: EntityList = [\"minecraft:armor_stand\", \"minecraft:zombie\", \"ageoftitans:reaper\"]");
			
			list = builder.worldRestart().define("EntityList", Lists.newArrayList("minecraft:armor_stand"));
			
			builder.pop();
			
		}
		
	}
	
	public static class Server {
		

		
		public Server(ForgeConfigSpec.Builder builder) {
			
		}
		
	}
	
//	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final ForgeConfigSpec COMMON_SPEC;
//	public static final ForgeConfigSpec SERVER_SPEC;
//	public static final Client CLIENT;
	public static final Common COMMON;
//	public static final Server SERVER;
	
	static {
//		final Pair<Client, ForgeConfigSpec> specPairClient = new ForgeConfigSpec.Builder().configure(Client::new);
//		CLIENT_SPEC = specPairClient.getRight();
//		CLIENT = specPairClient.getLeft();
		final Pair<Common, ForgeConfigSpec> specPairCommon = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPairCommon.getRight();
		COMMON = specPairCommon.getLeft();
//		final Pair<Server, ForgeConfigSpec> specPairServer = new ForgeConfigSpec.Builder().configure(Server::new);
//		SERVER_SPEC = specPairServer.getRight();
//		SERVER = specPairServer.getLeft();
	}
	
	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading event) {
		renderMobOverlay.initRenderableList();
	}
	
	@SubscribeEvent
	public static void onFileChange(final ModConfig.Reloading event) {
		
	}
	
}
