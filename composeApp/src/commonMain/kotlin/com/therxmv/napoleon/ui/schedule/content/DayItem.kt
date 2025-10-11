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
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.therxmv.napoleon.base.ui.CopyIconButton
import com.therxmv.napoleon.base.ui.LocalCopyIconColor
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiData
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiEvent
import com.therxmv.napoleon.ui.theme.NapoleonTheme

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
    val bottomCornerRadius = NapoleonTheme.shapes.noneCornerRadius.value
        .takeIf { data.isExpanded } ?: NapoleonTheme.shapes.cornerRadius.value
    val bottomRadius by animateFloatAsState(targetValue = bottomCornerRadius)

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = color,
        shape = RoundedCornerShape(
            topStart = NapoleonTheme.shapes.cornerRadius,
            topEnd = NapoleonTheme.shapes.cornerRadius,
            bottomEnd = bottomRadius.dp,
            bottomStart = bottomRadius.dp,
        ),
        onClick = onExpand,
    ) {
        Row(
            modifier = Modifier.padding(NapoleonTheme.paddings.startAndHalfVerticalValues),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Name(
                modifier = Modifier.weight(1f),
                name = data.name,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.contentColorFor(color),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                ),
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
            .border(NapoleonTheme.paddings.divider, color, NapoleonTheme.shapes.allRounded)
            .padding(NapoleonTheme.paddings.defaultValues),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Name(
            modifier = Modifier.weight(1f),
            name = data.name,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
            ),
        )
    }
}

@Composable
private fun Name(
    modifier: Modifier = Modifier,
    name: String,
    style: TextStyle,
) {
    Text(
        modifier = modifier,
        text = name,
        style = style,
    )
}