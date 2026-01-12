package com.therxmv.leonui.tile

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.text.LeonTextSize

@Stable
sealed class LeonTileSize(val iconSize: Dp, val textSize: LeonTextSize) {

    object Small : LeonTileSize(24.dp, LeonTextSize.Body1)
    object Big : LeonTileSize(36.dp, LeonTextSize.Title1)
}