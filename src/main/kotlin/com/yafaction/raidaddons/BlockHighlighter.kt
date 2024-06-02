package com.yafaction.raidaddons

import net.fabricmc.api.ModInitializer
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.WorldRenderer
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper

object BlockHighlighter : ModInitializer {
    private val waypoints = mutableListOf<Waypoint>()

    override fun onInitialize() {
        WorldRenderEvents.END.register(WorldRenderEvents.End { context ->
            renderWaypoints(context.matrixStack(), context.consumers())
        })
    }

    private fun renderWaypoints(matrixStack: MatrixStack, vertexConsumers: VertexConsumerProvider?) {
        val client = MinecraftClient.getInstance()
        val cameraPos = client.gameRenderer.camera.pos

        waypoints.forEach { waypoint ->
            val pos = BlockPos(waypoint.x.toInt(), waypoint.y.toInt(), waypoint.z.toInt())
            val blockState = client.world?.getBlockState(pos)
            if (blockState != null) {
                val offset = Vec3d.of(pos).subtract(cameraPos)

                matrixStack.push()
                matrixStack.translate(offset.x, offset.y, offset.z)

                val vertexConsumer = vertexConsumers?.getBuffer(RenderLayer.getTranslucent())
                if (vertexConsumer != null) {
                    try {
                        WorldRenderer.drawBox(
                            matrixStack,
                            vertexConsumer,
                            0.0,
                            0.0,
                            0.0,
                            1.0,
                            1.0,
                            1.0,
                            //^^these are my vertexes, so its in order x1,y1,z1,x2,y2,z2
                            MathHelper.clamp((waypoint.color shr 16 and 0xFF) / 255.0f, 0.0f, 1.0f),
                            MathHelper.clamp((waypoint.color shr 8 and 0xFF) / 255.0f, 0.0f, 1.0f),
                            MathHelper.clamp((waypoint.color and 0xFF) / 255.0f, 0.0f, 1.0f),
                            0.5f
                        )
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                        //don't really have to worry about this
                    }
                }

                matrixStack.pop()
            }
        }
    }

    data class Waypoint(val x: Double, val y: Double, val z: Double, val color: Int)

    fun addWaypoint(x: Double, y: Double, z: Double, color: Int) {
        waypoints.add(Waypoint(x, y, z, color))
        //thing for add waypoint had a thing in Raidaddons.kt that called this
    }
}
