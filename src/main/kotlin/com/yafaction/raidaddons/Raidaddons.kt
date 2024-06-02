package com.yafaction.raidaddons

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Raidaddons : ModInitializer {
    private val logger = LoggerFactory.getLogger("raidaddons")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
		BlockHighlighter.onInitialize()
            val waypointColor = 0xFF0000
            BlockHighlighter.addWaypoint(0.0, -55.0, 0.0, waypointColor)
	}
}