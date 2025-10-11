package com.therxmv.napoleon.ui.schedule.component

sealed interface ScheduleUiEvent {

    data class ExpandDay(val name: String) : ScheduleUiEvent

    data object CopyDay : ScheduleUiEvent

    data object CopyLessonLink : ScheduleUiEvent
}