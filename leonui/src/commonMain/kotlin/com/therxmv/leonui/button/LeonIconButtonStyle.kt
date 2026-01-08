package com.therxmv.leonui.button

import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
sealed interface LeonIconButtonStyle {

    val colors: IconButtonColors
        @Composable get

    object Default : LeonIconButtonStyle {
        override val colors: IconButtonColors
            @Composable get() = IconButtonDefaults.iconButtonColors()
    }

    object Filled : LeonIconButtonStyle {
        override val colors: IconButtonColors
            @Composable get() = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
            )
    }
}