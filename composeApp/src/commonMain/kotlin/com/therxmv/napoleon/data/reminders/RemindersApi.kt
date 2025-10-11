package com.therxmv.napoleon.data.reminders

import com.therxmv.napoleon.data.reminders.event.model.EventModel
import com.therxmv.napoleon.data.reminders.event.model.ReminderModel

// Outdated/Deprecated
expect class RemindersApi() {

    fun addNotification(eventModel: EventModel, onComplete: (String) -> Unit)
    fun deleteNotification(reminderModel: ReminderModel)
    fun requestNotificationPermission()
    fun isPermissionGranted(onComplete: (Boolean) -> Unit)
}