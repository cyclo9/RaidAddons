package com.yafaction.raidaddons

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
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
    class PortalRenderer : BlockEntityRenderer<PortalBlockEntity> {
        override fun render(
                entity: PortalBlockEntity?,
                tickDelta: Float,
                matrices: MatrixStack?,
                vertexConsumers: VertexConsumerProvider?,
                light: Int,
                overlay: Int
        ) {
            val pos: BlockPos? = entity?.pos
            TODO("Rendering logic here plaz")
        }
    }

    class PortalBlockFactory : BlockEntityRendererFactory<PortalBlockEntity> {
        /** Attaches an instance of a PortalRenderer to an instance of PortalBlockEntity */
        override fun create(
                ctx: BlockEntityRendererFactory.Context?
        ): BlockEntityRenderer<PortalBlockEntity> {
            return PortalRenderer()
        }
    }

    override fun onInitialize() {
        // Register the entity type and portal block factory
        BlockEntityRendererFactories.register(PORTAL_BLOCK_ENTITY_TYPE, PortalBlockFactory())
        logger.info(PORTAL_BLOCK_ENTITY_TYPE.toString())
    }
}
