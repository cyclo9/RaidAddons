package com.yafaction.utils

enum class Room(val portal: Portal) {
    // TODO: Create room class, where each room should contain a map of portals

    ENTRANCE(Portal.GRAY),
    GRAY(Portal.WHITE),
    WHITE(Portal.BLACK),
    BLACK(Portal.ORANGE),
    ORANGE(Portal.BLUE),
    BLUE(Portal.PINK),
}
