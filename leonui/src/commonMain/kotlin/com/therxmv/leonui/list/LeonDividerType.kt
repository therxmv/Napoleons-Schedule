package com.therxmv.leonui.list

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.theme.LeonTheme

sealed class LeonDividerType(
    val width: Dp = Dp.Unspecified,
    val thickness: Dp = LeonTheme.paddings.divider,
) {
    data object Small : LeonDividerType(width = 50.dp, thickness = LeonTheme.paddings.divider.times(2))
    data object Full : LeonDividerType()
}