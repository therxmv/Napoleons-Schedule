package com.therxmv.napoleon.ui.schedule.content

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.base.ui.CopyIconButton
import com.therxmv.napoleon.base.ui.LocalCopyIconColor
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiData
import compose.icons.FeatherIcons
import compose.icons.feathericons.Link

@Composable
fun RowScope.ScheduleLessonContent(
    data: ScheduleUiData.Lesson,
    onCopyEvent: () -> Unit,
) {
    when (data) {
        is ScheduleUiData.Lesson.Offline -> OfflineLesson(data)
        is ScheduleUiData.Lesson.Online -> OnlineLesson(data, onCopyEvent)
        is ScheduleUiData.Lesson.ByTime -> TimeLesson(data)
        is ScheduleUiData.Lesson.Empty -> EmptyLesson(data)
    }
}

@Composable
private fun RowScope.OnlineLesson(
    data: ScheduleUiData.Lesson.Online,
    onCopyEvent: () -> Unit,
) {
    PrefixText(data.number)
    Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal))

    Name(
        modifier = Modifier.weight(1f),
        name = data.name,
    )

    CompositionLocalProvider(LocalCopyIconColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
        CopyIconButton(data.toString(), FeatherIcons.Link, onCopyEvent)
    }
}

@Composable
private fun RowScope.OfflineLesson(
    data: ScheduleUiData.Lesson.Offline,
) {
    PrefixText(data.number)
    Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal))

    Name(
        modifier = Modifier.weight(1f),
        name = data.name,
    )

    if (data.classroom != null) {
        Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal))
        SuffixText(data.classroom)
    }

    CompositionLocalProvider(LocalCopyIconColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
        CopyIconButton(data.toString())
    }
}

@Composable
private fun RowScope.TimeLesson(
    data: ScheduleUiData.Lesson.ByTime,
) {
    if (data.time != null) {
        PrefixText(data.time)
        Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal))
    }

    Name(
        modifier = Modifier.weight(1f),
        name = data.name,
    )

    CompositionLocalProvider(LocalCopyIconColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
        CopyIconButton(data.toString())
    }
}

@Composable
private fun RowScope.EmptyLesson(
    data: ScheduleUiData.Lesson.Empty,
) {
    if (data.number != null) {
        PrefixText(data.number)
        Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal))
    }

    Name(
        modifier = Modifier.weight(1f),
        name = data.name,
    )
}

@Composable
private fun PrefixText(text: String) {
    LeonText(
        modifier = Modifier.sizeIn(minWidth = 24.dp),
        text = text,
        weight = LeonTextWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun SuffixText(text: String) {
    LeonText(
        text = text,
        size = LeonTextSize.Body2,
        weight = LeonTextWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun Name(
    modifier: Modifier = Modifier,
    name: String,
) {
    LeonText(
        modifier = modifier,
        text = name,
        overflow = TextOverflow.Ellipsis,
        maxLines = 5,
    )
}