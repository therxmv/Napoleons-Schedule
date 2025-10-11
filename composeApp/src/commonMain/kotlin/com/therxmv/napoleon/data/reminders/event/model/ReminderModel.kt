package com.therxmv.napoleon.data.reminders.event.model

data class ReminderModel(
    val startDate: Long,
    val reminderId: String,
    val lessonId: String,
)