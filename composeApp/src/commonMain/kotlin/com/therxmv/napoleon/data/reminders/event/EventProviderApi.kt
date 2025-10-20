package com.therxmv.napoleon.data.reminders.event

import com.therxmv.napoleon.data.reminders.event.model.EventModel
import com.therxmv.napoleon.data.repository.specialty.model.LessonModel

interface EventProviderApi {
    fun getEvent(item: LessonModel, faculty: String): EventModel
}