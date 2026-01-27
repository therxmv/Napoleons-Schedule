package com.therxmv.leonui.button

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.extensions.applyIf
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonComponentPreview
import com.therxmv.leonui.theme.LeonTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.ArrowRight

@Composable
fun LeonButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    style: LeonButtonStyle = LeonButtonStyle.Default,
    isEnabled: Boolean = true,
    prefixIcon: ImageVector? = null,
    suffixIcon: ImageVector? = null,
) {
    val colors = style.colors
    Button(
        modifier = modifier
            .applyIf(style.withBorder()) {
                border(
                    LeonTheme.sizes.border,
                    LeonButtonStyle.borderColor,
                    LeonTheme.shapes.button
                )
            },
        colors = colors,
        shape = LeonTheme.shapes.button,
        onClick = onClick,
        enabled = isEnabled,
        contentPadding = style.contentPadding,
    ) {
        val contentColor = if (isEnabled) colors.contentColor else colors.disabledContentColor
        if (prefixIcon != null) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = prefixIcon,
                tint = contentColor,
                contentDescription = null,
            )
        }

        LeonText(
            modifier = Modifier.padding(
                horizontal = LeonTheme.paddings.horizontal.skinny,
                vertical = LeonTheme.paddings.vertical.base,
            ),
            text = label,
            weight = LeonTextWeight.Bold,
            color = contentColor,
        )

        if (suffixIcon != null) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = suffixIcon,
                tint = contentColor,
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun LeonButtonPreview(
    @PreviewParameter(LeonButtonStyleProvider::class) style: LeonButtonStyle,
) {
    val label = "Continue"
    LeonComponentPreview {
        LeonButton(
            label = label,
            style = style,
            onClick = {},
        )
        LeonButton(
            label = label,
            style = style,
            isEnabled = false,
            onClick = {},
        )
        LeonButton(
            label = label,
            style = style,
            prefixIcon = FeatherIcons.ArrowLeft,
            onClick = {},
        )
        LeonButton(
            label = label,
            style = style,
            suffixIcon = FeatherIcons.ArrowRight,
            onClick = {},
        )
    }
}

private class LeonButtonStyleProvider : PreviewParameterProvider<LeonButtonStyle> {
    override val values = sequenceOf(
        LeonButtonStyle.Default,
        LeonButtonStyle.Outlined,
        LeonButtonStyle.Text(),
    )
}