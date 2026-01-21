package com.therxmv.leonui.card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.therxmv.leonui.theme.LeonTheme
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

    data object Info : LeonCardType {
        override val icon
            get() = FeatherIcons.Info

        override val accent
            @Composable get() = LeonTheme.colors.surfaceTint

        override val containerColor
            @Composable get() = Color.Transparent

        override val contentColor
            @Composable get() = LeonTheme.colors.onSurface
    }

    data object Error : LeonCardType {
        override val icon
            get() = FeatherIcons.AlertTriangle

        override val accent
            @Composable get() = LeonTheme.colors.error

        override val containerColor
            @Composable get() = LeonTheme.colors.errorContainer

        override val contentColor
            @Composable get() = LeonTheme.colors.onErrorContainer
    }

    fun withBorder(): Boolean =
        this is Info
}