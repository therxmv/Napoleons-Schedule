package com.therxmv.leonui.input

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.text.toTextStyle

@Stable
sealed interface LeonTextInputStyle {

    val colors: TextFieldColors
        @Composable get

    val textStyle: TextStyle
        get() = LeonTextSize.Body1.toTextStyle(weight = LeonTextWeight.Bold)

    data object Primary : LeonTextInputStyle {
        override val colors: TextFieldColors
            @Composable get() = getTextFieldColors(
                accent = MaterialTheme.colorScheme.primary,
                selection = MaterialTheme.colorScheme.tertiary,
            )
    }

   data  object Tertiary : LeonTextInputStyle {
        override val colors: TextFieldColors
            @Composable get() = getTextFieldColors(
                accent = MaterialTheme.colorScheme.tertiary,
                selection = MaterialTheme.colorScheme.primary,
            )
    }

    companion object {
        @Composable
        private fun getTextFieldColors(accent: Color, selection: Color): TextFieldColors =
            OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                errorContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = accent,
                selectionColors = TextSelectionColors(selection, selection),
            )
    }
}