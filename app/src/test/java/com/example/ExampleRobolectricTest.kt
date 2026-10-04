package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.LoveLettersRepository
import com.example.data.LoveTimeTracker
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context verifies BM LOVE app name`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("BM LOVE", appName)
    }

    @Test
    fun `verify love time tracking calculation from Nov 10 2023`() {
        val timePassed = LoveTimeTracker.calculateTimePassed()
        assertTrue("Total days should be greater than 300", timePassed.totalDays > 300)
        assertTrue("Heartbeats should be positive", timePassed.estimatedHeartbeats > 0)
    }

    @Test
    fun `verify monthly letters are available and dynamic`() {
        val letter1 = LoveLettersRepository.getMonthlyLetter(1)
        assertNotNull(letter1)
        assertTrue(letter1.fullContent.isNotBlank())

        val letterYear1 = LoveLettersRepository.getMonthlyLetter(12)
        assertNotNull(letterYear1)
        assertTrue(letterYear1.title.contains("1er AÑO", ignoreCase = true))

        // Infinite generation test
        val futureLetter = LoveLettersRepository.getMonthlyLetter(100)
        assertNotNull(futureLetter)
        assertTrue(futureLetter.fullContent.contains("100 meses"))
    }
}
