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
        val isSchedule: Boolean
            get() = this is SkeletonTodaySchedule || this is TodaySchedule

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

        companion object {

            fun defaultSmallSquare(
                icon: ImageVector,
                title: String,
                onClick: () -> Unit,
            ): Default = Default(
                icon = icon,
                title = title,
                onClick = onClick,
                gridSpan = 1,
                ratio = 1f,
            )

            fun defaultWideRectangle(
                icon: ImageVector,
                title: String,
                onClick: () -> Unit,
            ): Default = Default(
                icon = icon,
                title = title,
                onClick = onClick,
                gridSpan = 2,
                ratio = 4f,
            )

            fun defaultSmallRectangle(
                icon: ImageVector,
                title: String,
                onClick: () -> Unit,
            ): Default = Default(
                icon = icon,
                title = title,
                onClick = onClick,
                gridSpan = 1,
                ratio = 2f,
            )
        }
    }
}