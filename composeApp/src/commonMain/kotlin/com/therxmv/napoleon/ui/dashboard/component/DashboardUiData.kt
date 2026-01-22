package com.therxmv.napoleon.ui.dashboard.component

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.therxmv.leonui.tile.LeonTileSize
import com.therxmv.leonui.tile.LeonTileType
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiData

@Immutable
data class DashboardUiData(
    val widgets: List<Widget>,
    val tiles: List<Tile>,
    val cacheReason: String? = null,
) {
    sealed interface Widget {
        val isSchedule: Boolean
            get() = this is SkeletonTodaySchedule || this is TodaySchedule

        data object SkeletonTodaySchedule : Widget
        data class TodaySchedule(val day: ScheduleUiData.Day) : Widget
    }

    sealed class Tile(open val gridSpan: Int) {

        data class Default(
            val icon: ImageVector,
            val title: String,
            val onClick: () -> Unit,
            override val gridSpan: Int,
            val ratio: Float,
        ) : Tile(gridSpan) {

            val size: LeonTileSize
                get() = if (isSquare() || isSingleColumn().not()) LeonTileSize.Big else LeonTileSize.Small

            val type: LeonTileType
                get() = if (isSingleColumn() && isSquare()) LeonTileType.Vertical else LeonTileType.Horizontal

            fun isSquare(): Boolean = ratio == SQUARE_RATIO
            fun isSingleColumn(): Boolean = gridSpan == ONE_COLUMN
        }

        data object EmptyDivider : Tile(TWO_COLUMN)

        companion object {

            private const val SQUARE_RATIO = 1f
            private const val HALF_RATIO = 2f
            private const val QUARTER_RATIO = 4f

            private const val ONE_COLUMN = 1
            private const val TWO_COLUMN = 2

            fun smallSquare(
                icon: ImageVector,
                title: String,
                onClick: () -> Unit,
            ): Default = Default(
                icon = icon,
                title = title,
                onClick = onClick,
                gridSpan = ONE_COLUMN,
                ratio = SQUARE_RATIO,
            )

            fun wideRectangle(
                icon: ImageVector,
                title: String,
                onClick: () -> Unit,
            ): Default = Default(
                icon = icon,
                title = title,
                onClick = onClick,
                gridSpan = TWO_COLUMN,
                ratio = QUARTER_RATIO,
            )

            fun smallRectangle(
                icon: ImageVector,
                title: String,
                onClick: () -> Unit,
            ): Default = Default(
                icon = icon,
                title = title,
                onClick = onClick,
                gridSpan = ONE_COLUMN,
                ratio = HALF_RATIO,
            )
        }
    }
}