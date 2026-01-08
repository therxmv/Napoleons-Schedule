package com.therxmv.leonui.button

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun LeonIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    tint: Color = LocalContentColor.current,
    onClick: () -> Unit,
    style: LeonIconButtonStyle = LeonIconButtonStyle.Default,
) {
    val colors = style.colors
    IconButton(
        modifier = modifier,
        colors = colors,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier
                .applyIf(style is LeonIconButtonStyle.Filled) {
                    fillMaxSize(0.45f)
                },
            imageVector = icon,
            tint = tint,
            contentDescription = null,
        )
    }
}