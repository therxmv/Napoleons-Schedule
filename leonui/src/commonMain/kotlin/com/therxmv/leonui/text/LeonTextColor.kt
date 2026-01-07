package com.therxmv.leonui.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

// TODO probably delete
@Stable
sealed interface LeonTextColor {

    val value: Color
        @Composable @ReadOnlyComposable get

    object OnSurface : LeonTextColor {
        override val value: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.onSurface
    }

    object SurfaceTint : LeonTextColor {
        override val value: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.surfaceTint
    }

    object Error : LeonTextColor {
        override val value: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.error
    }

    object OnErrorContainer : LeonTextColor {
        override val value: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.onErrorContainer
    }

    object OnPrimary : LeonTextColor {
        override val value: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.onPrimary
    }

    object OnTertiary : LeonTextColor {
        override val value: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.onTertiary
    }
}