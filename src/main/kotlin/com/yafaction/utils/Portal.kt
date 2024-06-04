package com.yafaction.utils

import net.minecraft.block.Block
import net.minecraft.block.Blocks

val BLOCK: Block? = Blocks.BLUE_TERRACOTTA

/** A portal is represented by the color and their coords */
enum class Portal(val color: String, val coords: Any?) {
    // TODO: in the future, i would like the portal to just contain a callable highlight function

    WHITE("white", null),
    ORANGE("orange", null),
    BLACK("black", null),
    GRAY("gray", null),
    BLUE("blue", null),
    PINK("pink", null) // NOTE: the PINK portal is the EXIT portal
}
