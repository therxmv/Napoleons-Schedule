package com.therxmv.leonui.button

import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.therxmv.leonui.theme.LeonTheme

@Stable
sealed interface LeonIconButtonStyle {

    val colors: IconButtonColors
        @Composable get

    data object Default : LeonIconButtonStyle {
        override val colors: IconButtonColors
            @Composable get() = IconButtonDefaults.iconButtonColors()
    }

    data class Filled(
        val containerColor: Color? = null,
    ) : LeonIconButtonStyle {
        override val colors: IconButtonColors
            @Composable get() = IconButtonDefaults.filledIconButtonColors(
                containerColor = containerColor ?: LeonTheme.colors.tertiary,
                contentColor = LeonTheme.colors.onTertiary,
            )
    }
}