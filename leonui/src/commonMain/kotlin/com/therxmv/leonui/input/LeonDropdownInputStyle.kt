package com.therxmv.leonui.input

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.therxmv.leonui.theme.LeonTheme

@Stable
sealed interface LeonDropdownInputStyle {

    val colors: TextFieldColors
        @Composable get

    @OptIn(ExperimentalMaterial3Api::class)
    data object Primary : LeonDropdownInputStyle {
        override val colors: TextFieldColors
            @Composable get() = ExposedDropdownMenuDefaults.textFieldColors(
                unfocusedContainerColor = LeonTheme.colors.primary,
                focusedContainerColor = LeonTheme.colors.primary,
                disabledContainerColor = LeonTheme.colors.surfaceVariant,

                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,

                focusedTrailingIconColor = LeonTheme.colors.onPrimary,
                unfocusedTrailingIconColor = LeonTheme.colors.onPrimary,

                unfocusedTextColor = LeonTheme.colors.onPrimary,
                focusedTextColor = LeonTheme.colors.onPrimary,

                unfocusedPrefixColor = LeonTheme.colors.onPrimary,
                focusedPrefixColor = LeonTheme.colors.onPrimary,

                unfocusedPlaceholderColor = LeonTheme.colors.onPrimary,
                focusedPlaceholderColor = LeonTheme.colors.onPrimary,
            )
    }
}