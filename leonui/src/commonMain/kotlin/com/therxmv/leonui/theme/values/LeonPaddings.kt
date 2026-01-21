package com.therxmv.leonui.theme.values

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object LeonPaddings {

    val horizontal = object : BasePadding {
        override val base = 16.dp

        override val baggy = 24.dp

        override val skinny = 8.dp
    }

    val vertical = object : BasePadding {
        override val base = 16.dp

        override val baggy = 24.dp

        override val skinny = 8.dp
    }

    val baseValues = PaddingValues(
        horizontal = horizontal.base,
        vertical = vertical.base,
    )
}

interface BasePadding {
    val base: Dp
    val baggy: Dp
    val skinny: Dp
}