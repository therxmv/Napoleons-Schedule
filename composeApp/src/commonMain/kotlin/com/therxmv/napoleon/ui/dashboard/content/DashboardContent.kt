package com.therxmv.napoleon.ui.dashboard.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.therxmv.leonui.card.LeonCard
import com.therxmv.leonui.card.LeonCardType
import com.therxmv.leonui.list.LeonDividerType
import com.therxmv.leonui.list.LeonHorizontalDivider
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.leonui.tile.LeonTile
import com.therxmv.napoleon.ui.PreviewMockData
import com.therxmv.napoleon.ui.dashboard.component.DashboardUiData
import com.therxmv.napoleon.ui.dashboard.component.DashboardUiEvent
import com.therxmv.napoleon.ui.dashboard.content.widget.ScheduleWidget
import com.therxmv.napoleon.ui.dashboard.content.widget.SkeletonWidget
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    data: DashboardUiData,
    onEvent: (DashboardUiEvent) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = LeonTheme.paddings.defaultValues,
        horizontalArrangement = Arrangement.spacedBy(LeonTheme.paddings.horizontal),
        verticalArrangement = Arrangement.spacedBy(LeonTheme.paddings.vertical),
    ) {
        if (data.cacheReason != null) {
            item(span = { GridItemSpan(2) }) {
                LeonCard(
                    text = data.cacheReason,
                    type = LeonCardType.Error,
                )
            }
        }

        widgets(data.widgets, onEvent)

        tiles(data.tiles)
    }
}

private fun LazyGridScope.widgets(
    list: List<DashboardUiData.Widget>,
    onEvent: (DashboardUiEvent) -> Unit,
) {
    items(
        items = list,
        span = { GridItemSpan(2) },
        key = { it.toString() },
    ) { data ->
        when (data) {
            DashboardUiData.Widget.SkeletonTodaySchedule -> {
                SkeletonWidget(
                    modifier = Modifier.animateItem(),
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }

            is DashboardUiData.Widget.TodaySchedule -> {
                ScheduleWidget(
                    modifier = Modifier.animateItem(),
                    day = data.day,
                    onEvent = onEvent,
                )
            }
        }
    }
}

private fun LazyGridScope.tiles(list: List<DashboardUiData.Tile>) {
    itemsIndexed(
        items = list,
        span = { _, data -> GridItemSpan(data.gridSpan) },
        key = { index, data -> "$index-$data" },
    ) { index, data ->
        val background = MaterialTheme.colorScheme.primaryContainer
            .takeIf { index % 2 == 0 } ?: MaterialTheme.colorScheme.tertiaryContainer

        when (data) {
            is DashboardUiData.Tile.Default -> {
                LeonTile(
                    modifier = Modifier.animateItem(),
                    size = data.size,
                    type = data.type,
                    icon = data.icon,
                    title = data.title,
                    ratio = data.ratio,
                    background = background,
                    onClick = data.onClick,
                )
            }

            DashboardUiData.Tile.EmptyDivider -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = LeonTheme.paddings.vertical)
                        .animateItem(),
                    contentAlignment = Alignment.Center,
                ) {
                    LeonHorizontalDivider(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        type = LeonDividerType.Full,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DashboardContentPreview() {
    LeonPreview {
        DashboardContent(
            data = PreviewMockData.dashboardUiData,
            onEvent = {},
        )
    }
}