package com.therxmv.napoleon.ui.schedule.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.therxmv.leonui.list.LeonEmptyExpandableHeader
import com.therxmv.leonui.list.LeonExpandableHeader
import com.therxmv.leonui.list.LeonExpandableSubItem
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.base.ui.CopyIconButton
import com.therxmv.napoleon.base.ui.LocalCopyIconColor
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiData
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiEvent

fun LazyListScope.scheduleDayOfWeekItem(
    modifier: Modifier = Modifier,
    data: ScheduleUiData.Day,
    color: Color,
    onEvent: (ScheduleUiEvent) -> Unit,
) {
    when (data) {
        is ScheduleUiData.Day.Default -> {
            item(key = data.name, contentType = data) {
                DayItem(
                    modifier = modifier,
                    data = data,
                    color = color,
                    onEvent = onEvent,
                )
            }

            itemsIndexed(
                items = data.lessons,
                key = { _, item -> item.id },
                contentType = { _, item -> item },
            ) { index, lesson ->
                LessonItem(
                    data = data,
                    lesson = lesson,
                    isLast = index == data.lessons.lastIndex,
                    onCopyEvent = { onEvent(ScheduleUiEvent.CopyLessonLink) },
                )
            }
        }

        is ScheduleUiData.Day.Empty -> {
            item(key = data.name, contentType = data) {
                LeonEmptyExpandableHeader(
                    modifier = modifier,
                    color = color,
                    content = { ScheduleEmptyDayContent(data = data) },
                )
            }
        }
    }
}

@Composable
fun RowScope.ScheduleDayContent(
    modifier: Modifier = Modifier,
    data: ScheduleUiData.Day.Default,
    color: Color,
    onCopyEvent: () -> Unit,
) {
    LeonText(
        modifier = modifier.weight(1f),
        text = data.name,
        size = LeonTextSize.Title2,
        color = LeonTheme.colors.contentColorFor(color),
        weight = LeonTextWeight.Bold,
    )

    CompositionLocalProvider(LocalCopyIconColor provides LeonTheme.colors.contentColorFor(color)) {
        CopyIconButton(textToCopy = data.toString(), onClick = onCopyEvent)
    }
}

@Composable
fun RowScope.ScheduleEmptyDayContent(
    modifier: Modifier = Modifier,
    data: ScheduleUiData.Day.Empty,
) {
    LeonText(
        modifier = modifier.weight(1f),
        text = data.name,
        size = LeonTextSize.Title2,
        color = LeonTheme.colors.onSurfaceVariant,
    )
}

@Composable
private fun DayItem(
    modifier: Modifier = Modifier,
    data: ScheduleUiData.Day.Default,
    color: Color,
    onEvent: (ScheduleUiEvent) -> Unit,
) {
    LeonExpandableHeader(
        modifier = modifier,
        color = color,
        isExpanded = data.isExpanded,
        onClick = { onEvent(data.expandEvent) },
    ) {
        ScheduleDayContent(
            data = data,
            color = color,
            onCopyEvent = { onEvent(ScheduleUiEvent.CopyDay) },
        )
    }
}

@Composable
private fun LessonItem(
    data: ScheduleUiData.Day.Default,
    lesson: ScheduleUiData.Lesson,
    isLast: Boolean,
    onCopyEvent: () -> Unit,
) {
    AnimatedVisibility(
        visible = data.isExpanded,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
    ) {
        LeonExpandableSubItem(
            isLast = isLast,
            onClick = (lesson as? ScheduleUiData.Lesson.Online)?.onClick,
        ) {
            ScheduleLessonContent(
                data = lesson,
                onCopyEvent = onCopyEvent,
            )
        }
    }
}