package com.therxmv.leonui.list

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.therxmv.leonui.extensions.applyIf
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.theme.LeonComponentPreview
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.leonui.theme.values.LeonSizes.Corner.toCornerRadius
import com.therxmv.leonui.theme.values.RoundedCornerShape

@Composable
fun LeonExpandableHeader(
    modifier: Modifier = Modifier,
    color: Color,
    isExpanded: Boolean,
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val bottomRadius by animateDpAsState(targetValue = isExpanded.toCornerRadius())

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    top = LeonTheme.sizes.corner.defaultRadius,
                    bottom = bottomRadius,
                )
            )
            .background(color)
            .applyIf(onClick != null) {
                clickable(onClick = { onClick?.invoke() })
            }
            .padding(
                start = LeonTheme.paddings.horizontal.base,
                top = LeonTheme.paddings.vertical.skinny,
                bottom = LeonTheme.paddings.vertical.skinny,
            ),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Composable
fun LeonExpandableSubItem(
    isLast: Boolean,
    onClick: (() -> Unit)? = null,
    content: @Composable (RowScope.() -> Unit),
) {
    val shape = LeonTheme.shapes.onlyBottomRounded().takeIf { isLast }
        ?: LeonTheme.shapes.noneRounded

    Row(
        modifier = Modifier
            .padding(top = LeonTheme.sizes.divider.thin)
            .fillMaxWidth()
            .clip(shape)
            .background(LeonTheme.colors.surfaceVariant)
            .applyIf(onClick != null) {
                clickable { onClick?.invoke() }
            }
            .padding(
                start = LeonTheme.paddings.horizontal.base,
                top = LeonTheme.paddings.vertical.skinny,
                bottom = LeonTheme.paddings.vertical.skinny,
            ),
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
            .border(LeonTheme.sizes.border, color, LeonTheme.shapes.allRounded)
            .padding(LeonTheme.paddings.baseValues),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Preview
@Composable
private fun LeonExpandableItemsPreview() {
    LeonComponentPreview {
        LeonExpandableHeader(
            color = LeonTheme.colors.tertiary,
            isExpanded = false,
            content = { LeonText("Collapsed Header") },
        )

        LeonEmptyExpandableItem(
            color = LeonTheme.colors.tertiary,
            content = { LeonText("Empty Header") },
        )

        Column {
            LeonExpandableHeader(
                color = LeonTheme.colors.primary,
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