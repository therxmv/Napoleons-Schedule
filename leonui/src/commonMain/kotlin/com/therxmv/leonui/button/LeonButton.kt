package com.therxmv.leonui.button

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonTheme

// TODO move
inline fun Modifier.applyIf(
    predicate: Boolean,
    modifier: Modifier.() -> Modifier,
): Modifier =
    if (predicate) {
        this.modifier()
    } else {
        this
    }

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
            .applyIf(style is LeonButtonStyle.Outlined) {
                val outline = style as LeonButtonStyle.Outlined
                border(outline.borderWidth, outline.borderColor, LeonTheme.shapes.button)
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
            modifier = Modifier.padding(LeonTheme.paddings.buttonText),
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