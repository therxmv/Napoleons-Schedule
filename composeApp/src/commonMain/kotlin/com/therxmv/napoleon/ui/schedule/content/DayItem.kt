package com.therxmv.napoleon.ui.schedule.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.base.ui.CopyIconButton
import com.therxmv.napoleon.base.ui.LocalCopyIconColor
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiData
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiEvent

fun LazyListScope.dayOfWeek(
    modifier: Modifier = Modifier,
    data: ScheduleUiData.Day,
    color: Color,
    onEvent: (ScheduleUiEvent) -> Unit,
) {
    when (data) {
        is ScheduleUiData.Day.Default -> {
            item(key = data.name, contentType = data) {
                DefaultDay(
                    modifier = modifier,
                    data = data,
                    color = color,
                    onExpand = { onEvent(data.expandEvent) },
                    onCopyEvent = { onEvent(ScheduleUiEvent.CopyDay) },
                )
            }

            lessonItems(data, onEvent)
        }

        is ScheduleUiData.Day.Empty -> {
            item(key = data.name, contentType = data) {
                EmptyDay(
                    modifier = modifier,
                    data = data,
                    color = color,
                )
            }
        }
    }
}

fun LazyListScope.lessonItems(
    data: ScheduleUiData.Day.Default,
    onEvent: (ScheduleUiEvent) -> Unit,
) {
    itemsIndexed(
        items = data.lessons,
        key = { _, item -> item.id },
        contentType = { _, item -> item },
    ) { index, lesson ->
        AnimatedVisibility(
            visible = data.isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),
        ) {
            Lesson(
                data = lesson,
                isLast = index == data.lessons.lastIndex,
                onCopyEvent = { onEvent(ScheduleUiEvent.CopyLessonLink) },
            )
        }
    }
}

@Composable
fun DefaultDay(
    modifier: Modifier = Modifier,
    data: ScheduleUiData.Day.Default,
    color: Color,
    onCopyEvent: () -> Unit,
    onExpand: () -> Unit,
) {
    val bottomCornerRadius = LeonTheme.shapes.noneCornerRadius.value
        .takeIf { data.isExpanded } ?: LeonTheme.shapes.cornerRadius.value
    val bottomRadius by animateFloatAsState(targetValue = bottomCornerRadius)

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = color,
        shape = RoundedCornerShape(
            topStart = LeonTheme.shapes.cornerRadius,
            topEnd = LeonTheme.shapes.cornerRadius,
            bottomEnd = bottomRadius.dp,
            bottomStart = bottomRadius.dp,
        ),
        onClick = onExpand,
    ) {
        Row(
            modifier = Modifier.padding(LeonTheme.paddings.startAndHalfVerticalValues),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LeonText(
                modifier = Modifier.weight(1f),
                text = data.name,
                size = LeonTextSize.Title2,
                color = MaterialTheme.colorScheme.contentColorFor(color),
                weight = LeonTextWeight.Bold,
            )

            CompositionLocalProvider(LocalCopyIconColor provides MaterialTheme.colorScheme.contentColorFor(color)) {
                CopyIconButton(textToCopy = data.toString(), onClick = onCopyEvent)
            }
        }
    }
}

@Composable
fun EmptyDay(
    modifier: Modifier = Modifier,
    data: ScheduleUiData.Day.Empty,
    color: Color,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(LeonTheme.paddings.divider, color, LeonTheme.shapes.allRounded)
            .padding(LeonTheme.paddings.defaultValues),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LeonText(
            modifier = Modifier.weight(1f),
            text = data.name,
            size = LeonTextSize.Title2,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}