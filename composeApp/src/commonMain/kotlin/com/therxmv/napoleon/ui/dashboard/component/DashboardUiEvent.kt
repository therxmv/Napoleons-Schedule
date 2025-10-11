package com.therxmv.napoleon.ui.dashboard.component

import com.therxmv.napoleon.navigation.destination.child.ChildDestination
import com.therxmv.napoleon.navigation.destination.slot.SlotDestination

sealed interface DashboardUiEvent {

    data class Navigate(val destination: ChildDestination) : DashboardUiEvent
    data class OpenDialog(val destination: SlotDestination) : DashboardUiEvent
    data object CopyDay : DashboardUiEvent
    data object CopyLessonLink : DashboardUiEvent
}