package com.yafaction.raidaddons

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.BlockRenderManager
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.RotationAxis
import net.minecraft.util.math.random.Random
import org.slf4j.LoggerFactory

object Highlighter : ModInitializer {
    private val logger = LoggerFactory.getLogger("raidaddons/highlighting")

    /** Custom block entity used by a custom render to target specific portal blocks */
    class PortalBlockEntity(pos: BlockPos, state: BlockState) :
        BlockEntity(PORTAL_BLOCK_ENTITY_TYPE, pos, state) {}

    // This is to register the portal block to the portal block entity
    val PORTAL_BLOCK_ENTITY_TYPE: BlockEntityType<PortalBlockEntity> =
        Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier("yafaction", "portal_entity"),
            FabricBlockEntityTypeBuilder.create(::PortalBlockEntity, Blocks.BLUE_TERRACOTTA)
                .build()
        )

    // NOTE: only targets BlockEntities and not Blocks--hence the mixin injection
    class PortalRenderer(context: BlockEntityRendererFactory.Context) : BlockEntityRenderer<PortalBlockEntity> {

        override fun render(
            entity: PortalBlockEntity?,
            tickDelta: Float,
            matrices: MatrixStack?,
            vertexConsumers: VertexConsumerProvider?,
            light: Int,
            overlay: Int
        ) {
            if (entity == null || matrices == null || vertexConsumers == null) {
                return
            }
            // Push the current transformation matrix
            matrices.push()
            // Move the matrix stack to the position of the block(0.0, 0.0, 0.0) is just inside the block)
            matrices.translate(0.5, 0.5, 0.5)
            // Scale the block
            matrices.scale(1.0f, 1.0f, 1.0f)
            val vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getTranslucent())
            val blockRenderManager = MinecraftClient.getInstance().blockRenderManager
            val blockState = entity.cachedState

            // Render the block
            blockRenderManager.renderBlock(
                blockState,
                entity.pos,
                entity.world,
                matrices,
                vertexConsumer,
                false,
                Random.create()
            )
            // Pop the transformation matrix to restore previous state
            matrices.pop()
        }
    }

    class PortalBlockFactory : BlockEntityRendererFactory<PortalBlockEntity> {
        /** Attaches an instance of a PortalRenderer to an instance of PortalBlockEntity */
        override fun create(ctx: BlockEntityRendererFactory.Context): BlockEntityRenderer<PortalBlockEntity> {
            return PortalRenderer(ctx)
        }
    }

    override fun onInitialize() {
        // Register the entity type and portal block factory
        BlockEntityRendererFactories.register(PORTAL_BLOCK_ENTITY_TYPE, PortalBlockFactory())
        logger.info(PORTAL_BLOCK_ENTITY_TYPE.toString())
    }
}