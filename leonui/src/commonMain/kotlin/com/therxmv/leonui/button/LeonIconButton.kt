package com.therxmv.leonui.button

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.therxmv.leonui.extensions.applyIf
import com.therxmv.leonui.theme.LeonComponentPreview
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

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

@Preview
@Composable
private fun LeonIconButtonPreview(
    @PreviewParameter(LeonIconButtonStyleProvider::class) style: LeonIconButtonStyle,
) {
    LeonComponentPreview {
        LeonIconButton(
            icon = FeatherIcons.Check,
            style = style,
            onClick = {},
        )
    }
}

private class LeonIconButtonStyleProvider : PreviewParameterProvider<LeonIconButtonStyle> {
    override val values = sequenceOf(
        LeonIconButtonStyle.Default,
        LeonIconButtonStyle.Filled,
    )
}