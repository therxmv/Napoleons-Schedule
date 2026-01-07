package com.therxmv.leonui.theme

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@Composable
fun LeonPreview(content: @Composable () -> Unit) {
    LeonTheme(isDarkTheme = false) {
        Surface(content = content)
    }
}