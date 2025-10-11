package com.therxmv.napoleon.data.reminders.event.model

data class EventModel(
    val title: String,
    val description: String,
    val reminderModel: ReminderModel,
)
