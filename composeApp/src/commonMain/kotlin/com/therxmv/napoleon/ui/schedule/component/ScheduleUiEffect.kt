package com.therxmv.napoleon.ui.schedule.component

sealed interface ScheduleUiEffect {

    class OpenWebUrl(val url: String) : ScheduleUiEffect
}