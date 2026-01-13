package com.therxmv.napoleon.ui.schedule.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.card.LeonCard
import com.therxmv.leonui.card.LeonCardType
import com.therxmv.leonui.extensions.applyIf
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.ui.PreviewMockData
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiData
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiEvent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ScheduleContent(
    modifier: Modifier = Modifier,
    data: ScheduleUiData,
    fallbackReason: String?,
    onEvent: (ScheduleUiEvent) -> Unit,
) {
    val dayModifier = remember {
        { isFirst: Boolean ->
            Modifier.applyIf(isFirst.not()) {
                padding(top = LeonTheme.paddings.vertical)
            }
        }
    }
    val oddColor = MaterialTheme.colorScheme.tertiary
    val evenColor = MaterialTheme.colorScheme.primary

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = LeonTheme.paddings.defaultValues,
    ) {
        if (fallbackReason != null) {
            item {
                LeonCard(
                    text = fallbackReason,
                    type = LeonCardType.Error,
                )
                Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical))
            }
        }

        data.days.forEachIndexed { index, day ->
            scheduleDayOfWeekItem(
                modifier = dayModifier(index == 0),
                data = day,
                color = evenColor.takeIf { index % 2 == 0 } ?: oddColor,
                onEvent = onEvent
            )
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Preview
@Composable
private fun ScheduleContentPreview() {
    LeonPreview {
        ScheduleContent(
            data = PreviewMockData.scheduleUiData,
            fallbackReason = "Fallback Reason",
            onEvent = {},
        )
    }
}