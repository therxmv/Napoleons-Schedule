package com.therxmv.leonui.text

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Stable
sealed class LeonTextSize(val value: TextUnit, val lineHeight: TextUnit) {

    object Title1 : LeonTextSize(
        value = 24.sp,
        lineHeight = 32.sp,
    )

    object Title2 : LeonTextSize(
        value = 20.sp,
        lineHeight = 28.sp,
    )

    object Body1 : LeonTextSize(
        value = 16.sp,
        lineHeight = 24.sp,
    )

    object Body2 : LeonTextSize(
        value = 14.sp,
        lineHeight = 20.sp,
    )
}