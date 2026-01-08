package com.therxmv.leonui.button

import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.therxmv.leonui.theme.LeonTheme

@Stable
sealed interface LeonIconButtonStyle {

    val colors: IconButtonColors
        @Composable get

    object Default : LeonIconButtonStyle {
        override val colors: IconButtonColors
            @Composable get() = LeonTheme.colors.button.icon
    }

    object Filled : LeonIconButtonStyle {
        override val colors: IconButtonColors
            @Composable get() = LeonTheme.colors.button.filledIcon
    }
}