package com.therxmv.napoleon.data.source.local.reminders

import com.therxmv.napoleon.data.reminders.event.model.EventModel
import com.therxmv.napoleon.data.reminders.event.model.ReminderModel

interface RemindersLocalSourceApi {
    fun getAllReminders(): List<ReminderModel>
    fun isReminderExists(eventModel: EventModel): Boolean
    fun setReminder(eventModel: EventModel)
    fun deleteReminder(reminderId: String)
    fun clearOldReminders()
}