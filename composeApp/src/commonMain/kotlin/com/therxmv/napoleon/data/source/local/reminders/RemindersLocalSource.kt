package com.therxmv.napoleon.data.source.local.reminders

//class RemindersLocalSource(
//    databaseDriverFactory: DatabaseDriverFactory,
//) : RemindersLocalSourceApi {
//
//    private val database = Database(databaseDriverFactory.createDriver())
//
//    override fun getAllReminders(): List<ReminderModel> {
//        clearOldReminders()
//
//        return getReminders()
//    }
//
//    override fun isReminderExists(eventModel: EventModel) = getAllReminders().find {
//        eventModel.reminderModel.startDate == it.startDate
//                && eventModel.reminderModel.lessonId == it.lessonId
//    } != null
//
//    override fun setReminder(eventModel: EventModel) {
//        with(eventModel.reminderModel) {
//            database.reminderQueries.setReminder(
//                reminderId = reminderId,
//                lessonId = lessonId,
//                startDate = startDate.toString(),
//            )
//        }
//    }
//
//    override fun deleteReminder(reminderId: String) {
//        database.reminderQueries.deleteReminder(reminderId)
//    }
//
//    @OptIn(ExperimentalTime::class)
//    override fun clearOldReminders() {
//        getReminders().forEach {
//            if (it.startDate < Clock.System.now().toEpochMilliseconds()) {
//                deleteReminder(it.reminderId)
//            }
//        }
//    }
//
//    private fun getReminders() = database.reminderQueries
//        .getAllReminders()
//        .executeAsList()
//        .map { it.toDomain() }
//        .filter { it.lessonId.isNotEmpty() }
//}