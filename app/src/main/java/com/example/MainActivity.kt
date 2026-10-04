package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.alarm.AnniversaryAlarmScheduler
import com.example.audio.RomanticMusicPlayer
import com.example.components.RomanticBackground
import com.example.screens.AlarmSettingsScreen
import com.example.screens.CounterScreen
import com.example.screens.LettersScreen
import com.example.screens.MemoriesScreen
import com.example.ui.theme.MyApplicationTheme

enum class Screen(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val tag: String
) {
    COUNTER("Contador", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, "nav_counter"),
    LETTERS("Cartas", Icons.Filled.Mail, Icons.Outlined.Mail, "nav_letters"),
    MEMORIES("Recuerdos", Icons.Filled.Timeline, Icons.Outlined.Timeline, "nav_memories"),
    ALARM("Alarma", Icons.Filled.Alarm, Icons.Outlined.Alarm, "nav_alarm")
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Ensure 10th of the month at 6:00 AM alarm is scheduled
        if (AnniversaryAlarmScheduler.isAlarmEnabled(this)) {
            AnniversaryAlarmScheduler.scheduleMonthlyAnniversaryAlarm(this)
        }

        val openLetterMonth = intent.getIntExtra("OPEN_MONTH_LETTER", -1)

        setContent {
            MyApplicationTheme {
                BMLoveApp(
                    initialOpenMonthLetter = if (openLetterMonth > 0) openLetterMonth else null
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop audio playback to free resources if app is destroyed
        RomanticMusicPlayer.pause()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMLoveApp(
    initialOpenMonthLetter: Int? = null
) {
    var currentScreen by remember {
        mutableStateOf(if (initialOpenMonthLetter != null) Screen.LETTERS else Screen.COUNTER)
    }

    var selectedLetterMonth by remember {
        mutableStateOf(initialOpenMonthLetter)
    }

    // Handle Android system back button
    if (currentScreen != Screen.COUNTER) {
        BackHandler {
            currentScreen = Screen.COUNTER
        }
    }

    RomanticBackground {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "BM LOVE 💖",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.sp
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.92f)
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    Screen.values().forEach { screen ->
                        val selected = currentScreen == screen
                        NavigationBarItem(
                            selected = selected,
                            onClick = { currentScreen = screen },
                            icon = {
                                Icon(
                                    imageVector = if (selected) screen.selectedIcon else screen.unselectedIcon,
                                    contentDescription = screen.title
                                )
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            modifier = Modifier.testTag(screen.tag)
                        )
                    }
                }
            }
        ) { innerPadding ->
            when (currentScreen) {
                Screen.COUNTER -> CounterScreen(
                    onNavigateToLetters = { monthNum ->
                        selectedLetterMonth = monthNum
                        currentScreen = Screen.LETTERS
                    },
                    modifier = Modifier.padding(innerPadding)
                )

                Screen.LETTERS -> LettersScreen(
                    initialSelectedMonth = selectedLetterMonth,
                    modifier = Modifier.padding(innerPadding)
                )

                Screen.MEMORIES -> MemoriesScreen(
                    modifier = Modifier.padding(innerPadding)
                )

                Screen.ALARM -> AlarmSettingsScreen(
                    onViewLetterMonth = { monthNum ->
                        selectedLetterMonth = monthNum
                        currentScreen = Screen.LETTERS
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
