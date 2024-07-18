package net.shasankp000;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.shasankp000.Commands.spawnFakePlayer;

import net.shasankp000.OllamaClient.ollamaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static net.shasankp000.OllamaClient.ollamaClient.initializeOllamaClient;

public class AIPlayer implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("ai-player");
	private static final ExecutorService executor = Executors.newSingleThreadExecutor();


	@Override
	public void onInitialize() {

		LOGGER.info("Hello Fabric world!");

		spawnFakePlayer.register();
		spawnFakePlayer.additionalBotCommands();
		ollamaClient.getPlayerMessage();

		ServerLifecycleEvents.SERVER_STARTED.register(AIPlayer::onServerStarted);
	}

	private static void onServerStarted(MinecraftServer minecraftServer) {

		executor.submit(() -> {
			try {
				initializeOllamaClient(ollamaClient.botName);  // Replace "Steve" with your bot's default name
			} catch (Exception e) {
				LOGGER.error("Failed to initialize Ollama client", e);
			}
		});
	}

}
