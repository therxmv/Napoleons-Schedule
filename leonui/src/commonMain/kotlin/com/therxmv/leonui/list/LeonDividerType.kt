package com.therxmv.leonui.list

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.theme.LeonTheme

sealed class LeonDividerType(
    val width: Dp = Dp.Unspecified,
    val thickness: Dp = LeonTheme.sizes.divider.thin,
) {
    data object Small : LeonDividerType(width = 50.dp, thickness = LeonTheme.sizes.divider.thick)
    data object Full : LeonDividerType()
}