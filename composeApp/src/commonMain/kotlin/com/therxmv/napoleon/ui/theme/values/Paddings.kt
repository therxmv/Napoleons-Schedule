package com.therxmv.napoleon.ui.theme.values

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class NapoleonPaddings(
    val horizontal: Dp = 16.dp,
    val vertical: Dp = 16.dp,
    val halfHorizontal: Dp = horizontal / 2,
    val halfVertical: Dp = vertical / 2,
    val startAndHalfVerticalValues: PaddingValues = PaddingValues(
        start = horizontal,
        top = halfVertical,
        bottom = halfVertical,
    ),
    val defaultValues: PaddingValues = PaddingValues(
        horizontal = horizontal,
        vertical = vertical,
    ),
    val divider: Dp = 2.dp,
)