package com.therxmv.napoleon.navigation.destination.slot

import kotlinx.serialization.Serializable

@Serializable
sealed interface SlotDestination {

    @Serializable
    data object TimetableDialog : SlotDestination
}