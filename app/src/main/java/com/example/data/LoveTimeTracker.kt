package com.example.data

import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

data class TimePassed(
    val totalDays: Long,
    val totalHours: Long,
    val totalMinutes: Long,
    val totalSeconds: Long,
    val years: Int,
    val months: Int,
    val days: Int,
    val hours: Int,
    val minutes: Int,
    val seconds: Int,
    val estimatedHeartbeats: Long,
    val isAnniversaryDay: Boolean,
    val monthsTogether: Int,
    val daysUntilNextTenth: Int,
    val hoursUntilNextTenth: Int
)

object LoveTimeTracker {
    // Relationship started on November 10, 2023 at 00:00:00 local time
    val START_YEAR = 2023
    val START_MONTH = Calendar.NOVEMBER // 10 in 0-indexed Calendar
    val START_DAY = 10

    fun getStartDateCalendar(): Calendar {
        val cal = Calendar.getInstance()
        cal.set(START_YEAR, START_MONTH, START_DAY, 0, 0, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal
    }

    fun calculateTimePassed(nowMs: Long = System.currentTimeMillis()): TimePassed {
        val startCal = getStartDateCalendar()
        val startMs = startCal.timeInMillis
        val diffMs = (nowMs - startMs).coerceAtLeast(0L)

        val totalSeconds = diffMs / 1000
        val totalMinutes = totalSeconds / 60
        val totalHours = totalMinutes / 60
        val totalDays = totalHours / 24

        val nowCal = Calendar.getInstance()
        nowCal.timeInMillis = nowMs

        // Detailed date breakdown
        var years = nowCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR)
        var months = nowCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH)
        var days = nowCal.get(Calendar.DAY_OF_MONTH) - startCal.get(Calendar.DAY_OF_MONTH)

        if (days < 0) {
            months--
            val prevMonthCal = nowCal.clone() as Calendar
            prevMonthCal.add(Calendar.MONTH, -1)
            days += prevMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        if (months < 0) {
            years--
            months += 12
        }

        val hours = (totalHours % 24).toInt()
        val minutes = (totalMinutes % 60).toInt()
        val seconds = (totalSeconds % 60).toInt()

        // Total months together
        val totalMonths = (nowCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR)) * 12 +
                (nowCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH)) +
                (if (nowCal.get(Calendar.DAY_OF_MONTH) >= 10) 0 else -1)

        val isAnniversaryDay = nowCal.get(Calendar.DAY_OF_MONTH) == 10

        // Calculate next 10th at 6:00 AM
        val nextTenthCal = Calendar.getInstance()
        nextTenthCal.set(Calendar.DAY_OF_MONTH, 10)
        nextTenthCal.set(Calendar.HOUR_OF_DAY, 6)
        nextTenthCal.set(Calendar.MINUTE, 0)
        nextTenthCal.set(Calendar.SECOND, 0)
        nextTenthCal.set(Calendar.MILLISECOND, 0)

        if (nowCal.after(nextTenthCal)) {
            nextTenthCal.add(Calendar.MONTH, 1)
        }

        val diffUntilNextMs = (nextTenthCal.timeInMillis - nowMs).coerceAtLeast(0L)
        val daysUntilNextTenth = (diffUntilNextMs / (1000 * 60 * 60 * 24)).toInt()
        val hoursUntilNextTenth = ((diffUntilNextMs / (1000 * 60 * 60)) % 24).toInt()

        // Heartbeats (~80 beats per minute)
        val estimatedHeartbeats = totalMinutes * 80

        return TimePassed(
            totalDays = totalDays,
            totalHours = totalHours,
            totalMinutes = totalMinutes,
            totalSeconds = totalSeconds,
            years = years.coerceAtLeast(0),
            months = months.coerceAtLeast(0),
            days = days.coerceAtLeast(0),
            hours = hours.coerceAtLeast(0),
            minutes = minutes.coerceAtLeast(0),
            seconds = seconds.coerceAtLeast(0),
            estimatedHeartbeats = estimatedHeartbeats,
            isAnniversaryDay = isAnniversaryDay,
            monthsTogether = totalMonths.coerceAtLeast(0),
            daysUntilNextTenth = daysUntilNextTenth,
            hoursUntilNextTenth = hoursUntilNextTenth
        )
    }

    fun getNextAnniversaryCalendar(): Calendar {
        val now = Calendar.getInstance()
        val next = Calendar.getInstance()
        next.set(Calendar.DAY_OF_MONTH, 10)
        next.set(Calendar.HOUR_OF_DAY, 6)
        next.set(Calendar.MINUTE, 0)
        next.set(Calendar.SECOND, 0)
        next.set(Calendar.MILLISECOND, 0)

        if (now.after(next)) {
            next.add(Calendar.MONTH, 1)
        }
        return next
    }
}
