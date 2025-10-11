package com.therxmv.napoleon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.therxmv.napoleon.ui.theme.values.DarkColorScheme
import com.therxmv.napoleon.ui.theme.values.LightColorScheme
import com.therxmv.napoleon.ui.theme.values.NapoleonColors
import com.therxmv.napoleon.ui.theme.values.NapoleonPaddings
import com.therxmv.napoleon.ui.theme.values.NapoleonShapes

@Composable
fun NapoleonTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        isDarkTheme -> DarkColorScheme

        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}

object NapoleonTheme {
    val shapes = NapoleonShapes()

    val paddings = NapoleonPaddings()

    val colors = NapoleonColors
}