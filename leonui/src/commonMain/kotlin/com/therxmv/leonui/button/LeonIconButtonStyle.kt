package com.therxmv.leonui.button

import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.therxmv.leonui.theme.LeonTheme

@Stable
sealed interface LeonIconButtonStyle {

    val colors: IconButtonColors
        @Composable get

    data object Default : LeonIconButtonStyle {
        override val colors: IconButtonColors
            @Composable get() = IconButtonDefaults.iconButtonColors()
    }

    data object Filled : LeonIconButtonStyle {
        override val colors: IconButtonColors
            @Composable get() = IconButtonDefaults.filledIconButtonColors(
                containerColor = LeonTheme.colors.tertiary,
                contentColor = LeonTheme.colors.onTertiary,
            )
    }
}