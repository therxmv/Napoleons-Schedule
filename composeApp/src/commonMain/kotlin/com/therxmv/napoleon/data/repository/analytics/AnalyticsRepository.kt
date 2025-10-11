package com.therxmv.napoleon.data.repository.analytics

interface AnalyticsRepository {

    fun reportEvent(name: String)
    fun reportSpecialtySaved(faculty: String, specialty: String)
    fun reportScheduleOpened(faculty: String, specialty: String)
    fun reportNavigation(destination: String)
}

object AnalyticsEvents {

    const val NAVIGATION_EVENT = "home_item_click"
    const val DESTINATION_PARAM = "selected_screen"

    const val SPECIALTY_SAVED_EVENT = "save_specialty"
    const val FACULTY_PARAM = "faculty_param"
    const val SPECIALTY_PARAM = "specialty_param"

    const val TIMETABLE_CLICK = "calls_click"
    const val COPY_TIMETABLE = "calls_copy_click"

    const val SCHEDULE_OPENED_EVENT = "schedule_opened"
    const val SELECTED_FACULTY_PARAM = "selected_faculty"
    const val SELECTED_SPECIALTY_PARAM = "selected_specialty"
    const val OPEN_ONLINE_LESSON = "online_lesson_click"
    const val COPY_ONLINE_LINK = "copy_link_click"
    const val COPY_SCHEDULE = "schedule_copy_click"

    // TODO implement or remove
    const val SCHEDULE_SWITCH_CLICK = "schedule_switch_click"
    const val SET_REMINDER_CLICK = "set_reminder_click"
    const val DELETE_REMINDER_CLICK = "delete_reminder_click"
}