package com.therxmv.leonui.card

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.FeatherIcons
import compose.icons.feathericons.AlertTriangle
import compose.icons.feathericons.Info

@Stable
sealed interface LeonCardType {

    val icon: ImageVector

    val accent: Color
        @Composable get

    val containerColor: Color
        @Composable get

    val contentColor: Color
        @Composable get

    object Info : LeonCardType {
        override val icon
            get() = FeatherIcons.Info

        override val accent
            @Composable get() = MaterialTheme.colorScheme.surfaceTint

        override val containerColor
            @Composable get() = Color.Transparent

        override val contentColor
            @Composable get() = MaterialTheme.colorScheme.onSurface
    }

    object Error : LeonCardType {
        override val icon
            get() = FeatherIcons.AlertTriangle

        override val accent
            @Composable get() = MaterialTheme.colorScheme.error

        override val containerColor
            @Composable get() = MaterialTheme.colorScheme.errorContainer

        override val contentColor
            @Composable get() = MaterialTheme.colorScheme.onErrorContainer
    }

    fun withBorder(): Boolean =
        this is Info
}