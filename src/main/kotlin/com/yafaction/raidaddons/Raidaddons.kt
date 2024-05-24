package com.yafaction.raidaddons

import com.yafaction.utils.*
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.AttackBlockCallback
import net.minecraft.block.BlockState
import net.minecraft.util.ActionResult
import org.slf4j.LoggerFactory

object Raidaddons : ModInitializer {
    private val logger = LoggerFactory.getLogger("raidaddons")

    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        logger.info("Hello Fabric world!")

        AttackBlockCallback.EVENT.register { player, world, _, pos, _ ->
            val state: BlockState = world.getBlockState(pos)

            if (state.isToolRequired() &&
                            !player.isSpectator() &&
                            player.getMainHandStack().isEmpty()
            ) {
                logger.info("Dumbass player has tried to mine an hand-un-mine-able block")
            }

            ActionResult.PASS
        }
    }
}
