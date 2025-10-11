package com.therxmv.napoleon.ui.dashboard.component

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiData

@Immutable
data class DashboardUiData(
    val widgets: List<Widget>,
    val cards: List<Card>,
    val cacheReason: String? = null,
) {
    sealed interface Widget {
        data object SkeletonTodaySchedule : Widget
        data class TodaySchedule(val day: ScheduleUiData.Day) : Widget
    }

    sealed class Card(open val gridSpan: Int) {

        data class Default(
            val icon: ImageVector,
            val title: String,
            val onClick: () -> Unit,
            override val gridSpan: Int,
            val ratio: Float,
        ) : Card(gridSpan)

        data object EmptyDivider : Card(2)
    }
}