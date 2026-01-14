package com.therxmv.leonui.tile

import androidx.compose.runtime.Stable

@Stable
sealed interface LeonTileType {

    data object Vertical : LeonTileType
    data object Horizontal : LeonTileType
}