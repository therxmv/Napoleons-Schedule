package com.therxmv.leonui.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
sealed interface LeonButtonStyle {

    val colors: ButtonColors
        @Composable get

    val contentPadding: PaddingValues

    object Default : LeonButtonStyle {
        override val colors: ButtonColors
            @Composable get() = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
            )

        override val contentPadding: PaddingValues
            get() = ButtonDefaults.ContentPadding

    }

    object Outlined : LeonButtonStyle {
        override val colors: ButtonColors
            @Composable get() = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface,
            )

        override val contentPadding: PaddingValues
            get() = ButtonDefaults.ContentPadding

        val borderWidth: Dp
            get() = 2.dp

        val borderColor: Color
            @Composable get() = MaterialTheme.colorScheme.tertiary
    }

    object Text : LeonButtonStyle {
        override val colors: ButtonColors
            @Composable get() = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.surfaceTint,
            )

        override val contentPadding: PaddingValues
            get() = PaddingValues()
    }
}