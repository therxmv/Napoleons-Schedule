package com.therxmv.leonui.theme.values

import androidx.compose.foundation.shape.RoundedCornerShape

object LeonShapes {

    val allRounded = RoundedCornerShape(LeonSizes.corner.defaultRadius)
    val noneRounded = RoundedCornerShape(LeonSizes.corner.zeroRadius)

    val onlyTopRounded = RoundedCornerShape(
        topEnd = LeonSizes.corner.defaultRadius,
        topStart = LeonSizes.corner.defaultRadius,
        bottomEnd = LeonSizes.corner.zeroRadius,
        bottomStart = LeonSizes.corner.zeroRadius,
    )
    val onlyBottomRounded = RoundedCornerShape(
        topEnd = LeonSizes.corner.zeroRadius,
        topStart = LeonSizes.corner.zeroRadius,
        bottomEnd = LeonSizes.corner.defaultRadius,
        bottomStart = LeonSizes.corner.defaultRadius,
    )

    val button = RoundedCornerShape(LeonSizes.corner.largeRadius)
}