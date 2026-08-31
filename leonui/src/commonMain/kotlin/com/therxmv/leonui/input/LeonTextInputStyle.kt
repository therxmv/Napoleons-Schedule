package com.therxmv.leonui.input

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.text.toTextStyle
import com.therxmv.leonui.theme.LeonTheme

@Stable
sealed interface LeonTextInputStyle {

    val colors: TextFieldColors
        @Composable get

    val textStyle: TextStyle
        get() = LeonTextSize.Body1.toTextStyle(weight = LeonTextWeight.Bold)

    data object Primary : LeonTextInputStyle {
        override val colors: TextFieldColors
            @Composable get() = getTextFieldColors(
                accent = LeonTheme.colors.primary,
                selection = LeonTheme.colors.tertiary,
            )
    }

    data object Tertiary : LeonTextInputStyle {
        override val colors: TextFieldColors
            @Composable get() = getTextFieldColors(
                accent = LeonTheme.colors.tertiary,
                selection = LeonTheme.colors.primary,
            )
    }

    companion object {
        @Composable
        private fun getTextFieldColors(accent: Color, selection: Color): TextFieldColors =
            OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = LeonTheme.colors.surface,
                focusedContainerColor = LeonTheme.colors.surface,
                disabledContainerColor = LeonTheme.colors.surface,
                errorContainerColor = LeonTheme.colors.surface,
                unfocusedBorderColor = LeonTheme.colors.surface,
                focusedBorderColor = accent,
                selectionColors = TextSelectionColors(selection, selection),
            )
    }
}