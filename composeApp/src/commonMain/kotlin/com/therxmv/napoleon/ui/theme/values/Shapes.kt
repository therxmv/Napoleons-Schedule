package com.therxmv.napoleon.ui.theme.values

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class NapoleonShapes(
    val cornerRadius: Dp = 14.dp,
    val noneCornerRadius: Dp = 4.dp,
    val onlyTopRounded: Shape = RoundedCornerShape(
        topEnd = cornerRadius,
        topStart = cornerRadius,
        bottomEnd = noneCornerRadius,
        bottomStart = noneCornerRadius,
    ),
    val allRounded: Shape = RoundedCornerShape(cornerRadius),
    val noneRounded: Shape = RoundedCornerShape(noneCornerRadius),
    val onlyBottomRounded: Shape = RoundedCornerShape(
        topEnd = noneCornerRadius,
        topStart = noneCornerRadius,
        bottomEnd = cornerRadius,
        bottomStart = cornerRadius,
    ),
)