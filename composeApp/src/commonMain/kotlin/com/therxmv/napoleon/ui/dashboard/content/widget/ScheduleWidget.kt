package com.therxmv.napoleon.ui.dashboard.content.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.therxmv.napoleon.ui.dashboard.component.DashboardUiEvent
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiData
import com.therxmv.napoleon.ui.schedule.content.DefaultDay
import com.therxmv.napoleon.ui.schedule.content.EmptyDay
import com.therxmv.napoleon.ui.schedule.content.Lesson

@Composable
fun ScheduleWidget(
    modifier: Modifier = Modifier,
    day: ScheduleUiData.Day,
    onEvent: (DashboardUiEvent) -> Unit,
) {
    Column {
        when (day) {
            is ScheduleUiData.Day.Default -> {
                DefaultDay(
                    modifier = modifier,
                    data = day,
                    color = MaterialTheme.colorScheme.tertiary,
                    onCopyEvent = { onEvent(DashboardUiEvent.CopyDay) },
                    onExpand = {},
                )

                day.lessons.forEachIndexed { index, lesson ->
                    Lesson(
                        data = lesson,
                        isLast = index == day.lessons.lastIndex,
                        onCopyEvent = { onEvent(DashboardUiEvent.CopyLessonLink) },
                    )
                }
            }

            is ScheduleUiData.Day.Empty -> {
                EmptyDay(
                    modifier = modifier,
                    data = day,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}