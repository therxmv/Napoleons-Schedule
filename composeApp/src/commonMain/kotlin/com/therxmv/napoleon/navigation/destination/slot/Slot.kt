package com.therxmv.napoleon.navigation.destination.slot

import com.arkivanov.decompose.ComponentContext
import com.therxmv.napoleon.ui.timetable.component.TimetableComponent

sealed interface Slot {

    data class TimetableDialogSlot(val component: TimetableComponent) : Slot

    fun interface Factory {
        operator fun invoke(
            currentDestination: SlotDestination,
            componentContext: ComponentContext,
            dismiss: () -> Unit,
        ): Slot
    }
}