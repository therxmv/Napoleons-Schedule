package com.therxmv.leonui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.therxmv.leonui.theme.values.LeonColors
import com.therxmv.leonui.theme.values.LeonPaddings
import com.therxmv.leonui.theme.values.LeonShapes
import com.therxmv.leonui.theme.values.LeonSizes

@Composable
fun LeonTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = LeonColors.DarkScheme.takeIf { isDarkTheme } ?: LeonColors.LightScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}

object LeonTheme {

    val sizes = LeonSizes
    val paddings = LeonPaddings
    val shapes = LeonShapes

    val colors: ColorScheme
        @Composable get() = MaterialTheme.colorScheme
}