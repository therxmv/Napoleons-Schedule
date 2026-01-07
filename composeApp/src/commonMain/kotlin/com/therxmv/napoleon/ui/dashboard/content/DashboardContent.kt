package com.therxmv.napoleon.ui.dashboard.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.base.state.FallbackCard
import com.therxmv.napoleon.ui.dashboard.component.DashboardUiData
import com.therxmv.napoleon.ui.dashboard.component.DashboardUiEvent
import com.therxmv.napoleon.ui.dashboard.content.card.CardDivider
import com.therxmv.napoleon.ui.dashboard.content.card.DashboardCard
import com.therxmv.napoleon.ui.dashboard.content.widget.ScheduleWidget
import com.therxmv.napoleon.ui.dashboard.content.widget.SkeletonWidget

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
                FallbackCard(data.cacheReason)
            }
        }

        widgets(data.widgets, onEvent)

        cards(data.cards)
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

private fun LazyGridScope.cards(list: List<DashboardUiData.Card>) {
    itemsIndexed(
        items = list,
        span = { _, data -> GridItemSpan(data.gridSpan) },
        key = { index, data -> "$index-$data" },
    ) { index, data ->
        val background = MaterialTheme.colorScheme.primaryContainer
            .takeIf { index % 2 == 0 } ?: MaterialTheme.colorScheme.tertiaryContainer

        when (data) {
            is DashboardUiData.Card.Default -> {
                DashboardCard(
                    modifier = Modifier.animateItem(),
                    data = data,
                    background = background,
                )
            }

            DashboardUiData.Card.EmptyDivider -> {
                CardDivider(modifier = Modifier.animateItem())
            }
        }
    }
}