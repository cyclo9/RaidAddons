package com.yafaction.utils

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.Text

/**
 * Utilizes regex to retrieve the player's name from a PlayerEntity instance
 *
 * @param playerEntity (PlayerEntity)
 * @return player (String)
 */
fun getPlayerAsString(playerEntity: PlayerEntity): String? {
    val regex = """\['([^']+)'""".toRegex()
    val matchResult = regex.find(playerEntity.toString())
    return matchResult?.groups?.get(1)?.value
}

/**
 * Builds and returns a MutableText object as a string
 *
 * @param message (MutableText)
 * @return String
 */
fun getMessageAsString(message: Text): String {
    val messageBuilder = StringBuilder()
    val orderedText = message.asOrderedText()
    orderedText.accept { _, _, charPoint ->
        messageBuilder.append(charPoint.toChar())
        true
    }
    return messageBuilder.toString()
}
