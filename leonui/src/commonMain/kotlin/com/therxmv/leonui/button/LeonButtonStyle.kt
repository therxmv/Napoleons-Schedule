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
import com.therxmv.leonui.theme.LeonTheme

@Stable
sealed interface LeonButtonStyle {

    val colors: ButtonColors
        @Composable get

    val contentPadding: PaddingValues

    object Default : LeonButtonStyle {
        override val colors: ButtonColors
            @Composable get() = LeonTheme.colors.button.default

        override val contentPadding: PaddingValues
            get() = ButtonDefaults.ContentPadding

    }

    object Outlined : LeonButtonStyle {
        override val colors: ButtonColors
            @Composable get() = LeonTheme.colors.button.outlined

        override val contentPadding: PaddingValues
            get() = ButtonDefaults.ContentPadding

        val borderWidth: Dp
            get() = 2.dp

        val borderColor: Color
            @Composable get() = MaterialTheme.colorScheme.tertiary
    }

    object Text : LeonButtonStyle {
        override val colors: ButtonColors
            @Composable get() = LeonTheme.colors.button.text

        override val contentPadding: PaddingValues
            get() = PaddingValues()
    }
}