package com.therxmv.leonui.text

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Stable
sealed class LeonTextSize(val value: TextUnit, val lineHeight: TextUnit) {

    data object Title1 : LeonTextSize(
        value = 24.sp,
        lineHeight = 32.sp,
    )

    data object Title2 : LeonTextSize(
        value = 20.sp,
        lineHeight = 28.sp,
    )

    data object Body1 : LeonTextSize(
        value = 16.sp,
        lineHeight = 24.sp,
    )

    data object Body2 : LeonTextSize(
        value = 14.sp,
        lineHeight = 20.sp,
    )
}