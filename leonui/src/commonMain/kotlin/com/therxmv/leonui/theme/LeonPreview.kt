package com.therxmv.leonui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LeonPreview(content: @Composable () -> Unit) {
    LeonTheme(isDarkTheme = false) {
        Surface(content = content)
    }
}

@Composable
fun LeonComponentPreview(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    content: @Composable ColumnScope.() -> Unit,
) {
    LeonTheme(isDarkTheme = false) {
        Column(
            modifier = modifier
                .background(color)
                .padding(LeonTheme.paddings.defaultValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LeonTheme.paddings.vertical),
            content = content,
        )
    }
}