package com.therxmv.leonui.tile

import androidx.compose.runtime.Stable

@Stable
sealed interface LeonTileType {

    object Vertical : LeonTileType
    object Horizontal : LeonTileType
}