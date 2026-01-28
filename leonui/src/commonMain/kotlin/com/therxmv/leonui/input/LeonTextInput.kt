package com.therxmv.leonui.input

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.therxmv.leonui.theme.LeonComponentPreview
import com.therxmv.leonui.theme.LeonTheme

@Composable
fun LeonTextInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    error: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    style: LeonTextInputStyle = LeonTextInputStyle.Primary,
    maxLines: Int = Int.MAX_VALUE,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        isError = error != null,
        shape = LeonTheme.shapes.allRounded,
        textStyle = style.textStyle,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        colors = style.colors,
    )
}

@Preview
@Composable
private fun LeonTextInputPreview() {
    LeonComponentPreview(color = LeonTheme.colors.primary) {
        LeonTextInput(
            value = "input value",
            onValueChange = {},
        )

        LeonTextInput(
            value = "error value",
            error = "error",
            onValueChange = {},
        )
    }
}