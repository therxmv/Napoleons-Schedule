package com.therxmv.napoleon.ui.dashboard.content.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.therxmv.leonui.list.LeonEmptyExpandableItem
import com.therxmv.leonui.list.LeonExpandableHeader
import com.therxmv.leonui.list.LeonExpandableSubItem
import com.therxmv.napoleon.ui.dashboard.component.DashboardUiEvent
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiData
import com.therxmv.napoleon.ui.schedule.content.ScheduleDayContent
import com.therxmv.napoleon.ui.schedule.content.ScheduleEmptyDayContent
import com.therxmv.napoleon.ui.schedule.content.ScheduleLessonContent

@Composable
fun ScheduleWidget(
    modifier: Modifier = Modifier,
    day: ScheduleUiData.Day,
    onEvent: (DashboardUiEvent) -> Unit,
) {
    val color = MaterialTheme.colorScheme.tertiary

    Column {
        when (day) {
            is ScheduleUiData.Day.Default -> {
                LeonExpandableHeader(
                    modifier = modifier,
                    color = color,
                    isExpanded = true,
                ) {
                    ScheduleDayContent(
                        data = day,
                        color = color,
                        onCopyEvent = { onEvent(DashboardUiEvent.CopyDay) },
                    )
                }

                day.lessons.forEachIndexed { index, lesson ->
                    LeonExpandableSubItem(
                        isLast = index == day.lessons.lastIndex,
                        onClick = (lesson as? ScheduleUiData.Lesson.Online)?.onClick,
                    ) {
                        ScheduleLessonContent(
                            data = lesson,
                            onCopyEvent = { onEvent(DashboardUiEvent.CopyDay) },
                        )
                    }
                }
            }

            is ScheduleUiData.Day.Empty -> {
                LeonEmptyExpandableItem(
                    color = color,
                    content = { ScheduleEmptyDayContent(data = day) },
                )
            }
        }
    }
}