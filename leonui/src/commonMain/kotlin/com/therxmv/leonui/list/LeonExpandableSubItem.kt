package com.therxmv.leonui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.therxmv.leonui.button.LeonIconButton
import com.therxmv.leonui.extensions.applyIf
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.theme.LeonComponentPreview
import com.therxmv.leonui.theme.LeonTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.Trash2
import kotlin.math.roundToInt

@Composable
fun LeonExpandableSubItem(
    modifier: Modifier = Modifier,
    isLast: Boolean,
    paddingValues: PaddingValues = PaddingValues(
        start = LeonTheme.paddings.horizontal.base,
        top = LeonTheme.paddings.vertical.skinny,
        bottom = LeonTheme.paddings.vertical.skinny,
    ),
    onClick: (() -> Unit)? = null,
    content: @Composable (RowScope.() -> Unit),
) {
    val shape = LeonTheme.shapes.onlyBottomRounded().takeIf { isLast }
        ?: LeonTheme.shapes.noneRounded

    Row(
        modifier = modifier
            .padding(top = LeonTheme.sizes.divider.thin)
            .fillMaxWidth()
            .clip(shape)
            .background(LeonTheme.colors.surfaceVariant)
            .applyIf(onClick != null) {
                clickable { onClick?.invoke() }
            }
            .padding(paddingValues),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Composable
fun LeonSwipeableExpandableSubItem(
    modifier: Modifier = Modifier,
    isLast: Boolean,
    shouldResetState: Boolean = false,
    onStateChanged: (LeonSwipeState) -> Unit = {},
    actions: @Composable RowScope.() -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val density = LocalDensity.current
    var actionButtonsWidth by remember { mutableStateOf(0f) }

    val anchors = remember(actionButtonsWidth) {
        DraggableAnchors {
            LeonSwipeState.Start at 0f
            LeonSwipeState.End at -actionButtonsWidth
        }
    }

    val state = remember(anchors) {
        AnchoredDraggableState(
            initialValue = LeonSwipeState.Start,
            anchors = anchors,
        )
    }

    Box(modifier = modifier.height(IntrinsicSize.Max)) {
        Row(
            modifier = Modifier
                .onSizeChanged {
                    with(density) { actionButtonsWidth = it.width.toDp().toPx() }
                }
                .fillMaxHeight()
                .align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically,
            content = actions,
        )

        LeonExpandableSubItem(
            modifier = Modifier
                .fillMaxHeight()
                .offset { IntOffset(x = state.offset.roundToInt(), y = 0) }
                .anchoredDraggable(
                    state = state,
                    orientation = Orientation.Horizontal
                ),
            isLast = isLast,
            content = content,
        )
    }

    LaunchedEffect(shouldResetState) {
        if (shouldResetState) {
            state.animateTo(LeonSwipeState.Start)
        }
    }

    LaunchedEffect(state.currentValue) {
        onStateChanged(state.currentValue)
    }
}

enum class LeonSwipeState { Start, End }

@Preview
@Composable
private fun LeonExpandableItemsPreview() {
    LeonComponentPreview {
        Column {
            LeonExpandableHeader(
                color = LeonTheme.colors.primary,
                isExpanded = true,
                content = { LeonText("Expanded Header") },
            )

            LeonSwipeableExpandableSubItem(
                isLast = false,
                actions = { LeonIconButton(icon = FeatherIcons.Trash2, onClick = {}) },
                content = { LeonText("Swipeable item") },
            )

            repeat(2) {
                LeonExpandableSubItem(
                    isLast = it == 1,
                    onClick = {},
                    content = { LeonText("item$it") },
                )
            }
        }
    }
}