package com.example.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import com.example.alarm.AnniversaryAlarmScheduler
import com.example.data.LoveLettersRepository
import com.example.data.LoveTimeTracker

class AnniversaryAlarmReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "bm_love_anniversary_alarm_channel"
        const val NOTIFICATION_ID = 20231110
    }

    override fun onReceive(context: Context, intent: Intent) {
        val isBoot = intent.action == Intent.ACTION_BOOT_COMPLETED
        if (isBoot) {
            // Re-arm alarm after phone reboot
            if (AnniversaryAlarmScheduler.isAlarmEnabled(context)) {
                AnniversaryAlarmScheduler.scheduleMonthlyAnniversaryAlarm(context)
            }
            return
        }

        val isTest = intent.getBooleanExtra("IS_TEST", false)
        val timePassed = LoveTimeTracker.calculateTimePassed()

        val monthNumber = if (isTest) {
            intent.getIntExtra("MONTH_NUMBER", timePassed.monthsTogether.coerceAtLeast(1))
        } else {
            timePassed.monthsTogether.coerceAtLeast(1)
        }

        val letter = LoveLettersRepository.getMonthlyLetter(monthNumber)

        showAnniversaryNotification(context, monthNumber, letter.title, letter.notificationMessage, letter.fullContent)

        // If it was a real alarm, schedule the next month's 10th 6:00 AM
        if (!isTest && AnniversaryAlarmScheduler.isAlarmEnabled(context)) {
            AnniversaryAlarmScheduler.scheduleMonthlyAnniversaryAlarm(context)
        }
    }

    private fun showAnniversaryNotification(
        context: Context,
        monthNumber: Int,
        title: String,
        shortMessage: String,
        fullContent: String
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager ?: return

        createNotificationChannel(context, notificationManager)

        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("OPEN_MONTH_LETTER", monthNumber)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            monthNumber,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val vibrationPattern = longArrayOf(0, 400, 200, 400, 200, 800)

        val bigText = buildString {
            append("💌 ")
            append(shortMessage)
            append("\n\n")
            append(fullContent.take(280))
            if (fullContent.length > 280) append("...")
            append("\n\nToca para abrir la carta completa y celebrar juntos.")
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("¡Feliz Aniversario Mi Amor! 💖 ($monthNumber Meses)")
            .setContentText(shortMessage)
            .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVibrate(vibrationPattern)
            .setSound(soundUri)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(
                R.mipmap.ic_launcher,
                "Abrir Carta 💌",
                pendingIntent
            )
            .build()

        notificationManager.notify(NOTIFICATION_ID + monthNumber, notification)
    }

    private fun createNotificationChannel(context: Context, manager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Alarma de Aniversario BM LOVE"
            val descriptionText = "Alarma del día 10 de cada mes a las 6:00 AM con cartas de amor personalizadas"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 400, 200, 400, 200, 800)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), audioAttributes)
            }
            manager.createNotificationChannel(channel)
        }
    }
}
