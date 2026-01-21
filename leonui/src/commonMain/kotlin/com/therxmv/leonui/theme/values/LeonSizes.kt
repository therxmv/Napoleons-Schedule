package com.therxmv.leonui.theme.values

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object LeonSizes {

    val corner = Corner
    object Corner {
        val defaultRadius = 14.dp

        val largeRadius = 28.dp

        val zeroRadius = 4.dp

        fun Boolean.toCornerRadius(): Dp =
            if (this) zeroRadius else defaultRadius
    }

    val divider = Divider
    object Divider {
        val thick = 6.dp

        val thin = 2.dp
    }

    val border = 3.dp
}