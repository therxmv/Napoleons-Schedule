package com.therxmv.napoleon.ui.timetable.component

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

data class TimetableUiData(
    val icon: ImageVector,
    val titleRes: StringResource,
    val text: String,
    val copyLabelRes: StringResource,
    val closeLabelRes: StringResource,
)