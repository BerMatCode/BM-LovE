package com.example.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.data.LoveTimeTracker
import com.example.receiver.AnniversaryAlarmReceiver
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object AnniversaryAlarmScheduler {

    const val ACTION_TRIGGER_ANNIVERSARY = "com.example.ACTION_ANNIVERSARY_ALARM"
    private const val PREFS_NAME = "anniversary_alarm_prefs"
    private const val KEY_ALARM_ENABLED = "alarm_enabled"
    private const val REQUEST_CODE_ANNIVERSARY = 1010
    private const val REQUEST_CODE_TEST = 1011

    fun isAlarmEnabled(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_ALARM_ENABLED, true) // enabled by default
    }

    fun setAlarmEnabled(context: Context, enabled: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_ALARM_ENABLED, enabled).apply()
        if (enabled) {
            scheduleMonthlyAnniversaryAlarm(context)
        } else {
            cancelMonthlyAlarm(context)
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleMonthlyAnniversaryAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return

        val targetCal = LoveTimeTracker.getNextAnniversaryCalendar()
        val triggerMs = targetCal.timeInMillis

        val intent = Intent(context, AnniversaryAlarmReceiver::class.java).apply {
            action = ACTION_TRIGGER_ANNIVERSARY
            putExtra("TARGET_MS", triggerMs)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_ANNIVERSARY,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Use setAlarmClock or setExactAndAllowWhileIdle for reliable wakeup
                val showIntent = Intent(context, com.example.MainActivity::class.java)
                val showPendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    showIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                val alarmClockInfo = AlarmManager.AlarmClockInfo(triggerMs, showPendingIntent)
                alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerMs, pendingIntent)
            }
            Log.d("AnniversaryAlarm", "Scheduled next anniversary alarm for: ${targetCal.time}")
        } catch (e: SecurityException) {
            // Fallback to inexact if exact alarm permission was revoked
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerMs, pendingIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun cancelMonthlyAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, AnniversaryAlarmReceiver::class.java).apply {
            action = ACTION_TRIGGER_ANNIVERSARY
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_ANNIVERSARY,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
        }
    }

    /**
     * Trigger a test notification/alarm right now or in a few seconds so the user can verify it.
     */
    fun triggerImmediateTest(context: Context, monthNumber: Int = 12) {
        val receiver = AnniversaryAlarmReceiver()
        val testIntent = Intent(context, AnniversaryAlarmReceiver::class.java).apply {
            action = ACTION_TRIGGER_ANNIVERSARY
            putExtra("IS_TEST", true)
            putExtra("MONTH_NUMBER", monthNumber)
        }
        receiver.onReceive(context, testIntent)
    }

    fun getFormattedNextAlarmDate(): String {
        val cal = LoveTimeTracker.getNextAnniversaryCalendar()
        val sdf = SimpleDateFormat("EEEE d 'de' MMMM, yyyy - 06:00 AM", Locale("es", "ES"))
        return sdf.format(cal.time)
    }
}
