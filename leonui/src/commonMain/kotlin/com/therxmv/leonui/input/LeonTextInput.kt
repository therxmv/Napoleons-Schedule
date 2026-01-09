package com.therxmv.leonui.input

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.therxmv.leonui.theme.LeonTheme

@Composable
fun LeonTextInput(
    modifier: Modifier = Modifier,
    value: String,
    error: String?,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
    style: LeonTextInputStyle = LeonTextInputStyle.Primary,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        isError = error != null,
        shape = LeonTheme.shapes.allRounded,
        textStyle = style.textStyle,
        keyboardOptions = keyboardOptions,
        maxLines = 1,
        colors = style.colors,
    )
}