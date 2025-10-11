package com.therxmv.napoleon.data.reminders

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            resetReminders()
        }
    }

    private fun resetReminders() { // TODO 2 set reminders again after boot
//        val driver = DatabaseDriverFactory() // past context here
//        val source = RemindersLocalSource(driver)
//
//        source.getAllReminders().forEach {
//            source.deleteReminder(it.reminderId)
//        }
    }
}