package com.therxmv.leonui.theme.values

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp

object LeonShapes {

    val allRounded = RoundedCornerShape(size = LeonSizes.corner.defaultRadius)
    val largeRounded = RoundedCornerShape(size = LeonSizes.corner.largeRadius)
    val noneRounded = RoundedCornerShape(size = LeonSizes.corner.zeroRadius)

    val button = RoundedCornerShape(size = LeonSizes.corner.largeRadius)
    val tile = RoundedCornerShape(size = LeonSizes.corner.largeRadius)

    fun onlyTopRounded(top: Dp = LeonSizes.corner.defaultRadius): RoundedCornerShape =
        RoundedCornerShape(
            top = top,
            bottom = LeonSizes.corner.zeroRadius,
        )

    fun onlyBottomRounded(bottom: Dp = LeonSizes.corner.defaultRadius): RoundedCornerShape =
        RoundedCornerShape(
            top = LeonSizes.corner.zeroRadius,
            bottom = bottom,
        )
}

fun RoundedCornerShape(top: Dp, bottom: Dp): RoundedCornerShape =
    RoundedCornerShape(
        topStart = CornerSize(top),
        topEnd = CornerSize(top),
        bottomEnd = CornerSize(bottom),
        bottomStart = CornerSize(bottom),
    )