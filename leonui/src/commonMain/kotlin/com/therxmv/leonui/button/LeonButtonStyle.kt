package com.therxmv.leonui.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.therxmv.leonui.theme.LeonTheme

@Stable
sealed interface LeonButtonStyle {

    val colors: ButtonColors
        @Composable get

    val contentPadding: PaddingValues
        get() = ButtonDefaults.ContentPadding

    data object Default : LeonButtonStyle {
        override val colors: ButtonColors
            @Composable get() = ButtonDefaults.buttonColors(
                containerColor = LeonTheme.colors.tertiary,
                contentColor = LeonTheme.colors.onTertiary,
            )
    }

    data object Outlined : LeonButtonStyle {
        override val colors: ButtonColors
            @Composable get() = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = LeonTheme.colors.onSurface,
            )
    }

    data object Text : LeonButtonStyle {
        override val colors: ButtonColors
            @Composable get() = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                contentColor = LeonTheme.colors.surfaceTint,
            )

        override val contentPadding: PaddingValues
            get() = PaddingValues()
    }

    fun withBorder(): Boolean =
        this is Outlined

    companion object {
        val borderColor: Color
            @Composable get() = LeonTheme.colors.tertiary
    }
}