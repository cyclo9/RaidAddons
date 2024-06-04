package com.yafaction.raidaddons.mixin;

import com.yafaction.raidaddons.Highlighter;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.Block;

/**
 * This mixin connects all blue terracotta to the custom PortalBlocKEntity
 */

@Mixin(Block.class)
public abstract class PortalMixin implements BlockEntityProvider {
    private Logger logger = LoggerFactory.getLogger("raidaddons/mixin/portal");

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        if (this.equals(Blocks.BLUE_TERRACOTTA)) {
            logger.info(this.toString());
            return new Highlighter.PortalBlockEntity(pos, state);
        }
        return null;
    }
}
