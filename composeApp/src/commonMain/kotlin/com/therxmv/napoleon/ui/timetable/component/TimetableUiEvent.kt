package com.therxmv.napoleon.ui.timetable.component

sealed interface TimetableUiEvent {

    data object Dismiss : TimetableUiEvent

    data object Copy : TimetableUiEvent
}