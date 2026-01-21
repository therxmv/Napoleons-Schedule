package com.therxmv.napoleon.data.reminders

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

class ReminderReceiver : BroadcastReceiver() {

    companion object {
        private const val DEFAULT_ID = 0
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java,
        ) as NotificationManager

        notificationManager.sendReminder(context, intent)
    }

    private fun NotificationManager.sendReminder(context: Context, intent: Intent) {
//        val id = intent.getIntExtra(REMINDER_ID, DEFAULT_ID)
//        val title = intent.getStringExtra(REMINDER_TITLE)
//        val body = intent.getStringExtra(REMINDER_BODY)
//
//        val pendingIntent = PendingIntent.getActivity(
//            context,
//            0,
//            AppActivity.createIntent(context),
//            FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT,
//        )
//
//        val notification = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_icon)
//            .setContentTitle(title)
//            .setContentText(body)
//            .setContentIntent(pendingIntent)
//            .setPriority(PRIORITY_HIGH)
//            .setDefaults(DEFAULT_ALL)
//            .build()
//
//        notify(id, notification)
    }
}