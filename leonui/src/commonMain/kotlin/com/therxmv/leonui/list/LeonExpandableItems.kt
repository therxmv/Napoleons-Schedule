package com.therxmv.leonui.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.extensions.applyIf
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.theme.LeonComponentPreview
import com.therxmv.leonui.theme.LeonTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LeonExpandableHeader(
    modifier: Modifier = Modifier,
    color: Color,
    isExpanded: Boolean,
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val bottomCornerRadius = LeonTheme.shapes.noneCornerRadius.value
        .takeIf { isExpanded } ?: LeonTheme.shapes.cornerRadius.value
    val bottomRadius by animateFloatAsState(targetValue = bottomCornerRadius)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = LeonTheme.shapes.cornerRadius,
                    topEnd = LeonTheme.shapes.cornerRadius,
                    bottomEnd = bottomRadius.dp,
                    bottomStart = bottomRadius.dp,
                )
            )
            .background(color)
            .applyIf(onClick != null) {
                clickable(onClick = { onClick?.invoke() })
            }
            .padding(LeonTheme.paddings.startAndHalfVerticalValues),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Composable
fun LeonExpandableSubItem(
    isLast: Boolean,
    onClick: (() -> Unit)?,
    content: @Composable (RowScope.() -> Unit),
) {
    val shape = LeonTheme.shapes.onlyBottomRounded.takeIf { isLast }
        ?: LeonTheme.shapes.noneRounded

    Row(
        modifier = Modifier
            .padding(top = LeonTheme.paddings.divider)
            .fillMaxWidth()
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .applyIf(onClick != null) {
                clickable { onClick?.invoke() }
            }
            .padding(LeonTheme.paddings.startAndHalfVerticalValues),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Composable
fun LeonEmptyExpandableItem(
    modifier: Modifier = Modifier,
    color: Color,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(LeonTheme.paddings.border, color, LeonTheme.shapes.allRounded)
            .padding(LeonTheme.paddings.defaultValues),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Preview
@Composable
private fun LeonExpandableItemsPreview() {
    LeonComponentPreview {
        LeonExpandableHeader(
            color = MaterialTheme.colorScheme.tertiary,
            isExpanded = false,
            content = { LeonText("Collapsed Header") },
        )

        LeonEmptyExpandableItem(
            color = MaterialTheme.colorScheme.tertiary,
            content = { LeonText("Empty Header") },
        )

        Column {
            LeonExpandableHeader(
                color = MaterialTheme.colorScheme.primary,
                isExpanded = true,
                content = { LeonText("Expanded Header") },
            )

            repeat(4) {
                LeonExpandableSubItem(
                    isLast = it == 3,
                    onClick = {},
                    content = { LeonText("item$it") },
                )
            }
        }
    }
}