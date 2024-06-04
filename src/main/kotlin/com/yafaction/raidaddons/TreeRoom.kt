package com.yafaction.raidaddons

import com.yafaction.utils.*
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.player.PlayerEntity
import org.slf4j.LoggerFactory

object TreeRoom : ModInitializer {
    private val logger = LoggerFactory.getLogger("raidaddons/treeroom")

    private val client: MinecraftClient? = MinecraftClient.getInstance()
    private var player: String? = null

    override fun onInitialize() {
        ServerMessageEvents.CHAT_MESSAGE.register { message, sender, _ ->
            val player: String? = getPlayerAsString(sender)
            val messageText: String = message.signedBody().content()

            logger.info("[CHAT MESSAGE] $player said $messageText")
        }

        ServerMessageEvents.GAME_MESSAGE.register { _, message, _ ->
            // when receiving server messages, the mod attempts to retrieve and store
            // the player's username; this will be used in the future
            if (player == null && client?.player != null)
                    player = getPlayerAsString(client.player as PlayerEntity)

            logger.info("[PLAYER] $player")

            val messageAsStr: String = getMessageAsString(message)
            logger.info("[GAME MESSAGE] $messageAsStr")
        }
    }
}
