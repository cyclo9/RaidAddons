package com.yafaction.raidaddons

import com.yafaction.utils.*
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import org.slf4j.LoggerFactory

object TreeRoom : ModInitializer {
    private val logger = LoggerFactory.getLogger("raidaddons/treeroom")

    override fun onInitialize() {
        ServerMessageEvents.CHAT_MESSAGE.register { message, sender, _ ->
            val player: String? = getPlayerAsString(sender)
            val messageText: String = message.signedBody().content()

            logger.info("[CHAT MESSAGE] $player said $messageText")
        }

        ServerMessageEvents.GAME_MESSAGE.register { _, message, _ ->
            val messageAsStr: String = getMessageAsString(message)
            logger.info("[GAME MESSAGE] $messageAsStr")
        }
    }
}
