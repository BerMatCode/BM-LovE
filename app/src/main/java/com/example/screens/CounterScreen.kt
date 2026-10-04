package com.example.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.components.RomanticMusicBar
import com.example.data.LoveLettersRepository
import com.example.data.LoveTimeTracker
import com.example.data.TimePassed
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CounterScreen(
    onNavigateToLetters: (monthNumber: Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentTimeMs by remember { mutableLongStateOf(System.currentTimeMillis()) }

    // Live continuous ticker every second
    LaunchedEffect(Unit) {
        while (true) {
            currentTimeMs = System.currentTimeMillis()
            delay(1000)
        }
    }

    val timePassed = remember(currentTimeMs) {
        LoveTimeTracker.calculateTimePassed(currentTimeMs)
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse_heart")
    val heartbeatScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heartbeat"
    )

    val currentLetter = remember(timePassed.monthsTogether) {
        LoveLettersRepository.getMonthlyLetter(timePassed.monthsTogether.coerceAtLeast(1))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Music Player bar
        RomanticMusicBar(modifier = Modifier.padding(bottom = 12.dp))

        // Romantic Hero Card with BM LOVE Title
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("romantic_hero_card"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_romantic_hero),
                    contentDescription = "Nuestro amor romántico",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0x60000000),
                                    Color(0xD0380018)
                                )
                            )
                        )
                )

                // Hero Texts
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "BM LOVE",
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.AllInclusive,
                            contentDescription = "Amor Infinito",
                            tint = Color(0xFFFFB1C8),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = "Juntos desde el 10 de Noviembre del 2023",
                        color = Color(0xFFFFE4EC),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Anniversary Banner
        if (timePassed.isAnniversaryDay) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("anniversary_today_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFE4EC)
                ),
                border = BorderStroke(1.5.dp, Color(0xFFE11D48))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Celebration,
                        contentDescription = "Aniversario",
                        tint = Color(0xFFE11D48),
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "🎉 ¡HOY ES 10 DE ANIVERSARIO!",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF9F1239),
                            fontSize = 15.sp
                        )
                        Text(
                            text = "¡Feliz aniversario mi amor! Mes #${timePassed.monthsTogether}. ¡Cada día te amo más!",
                            fontSize = 13.sp,
                            color = Color(0xFF881337)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Live Infinity Counter Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(20.dp)
                    .scale(heartbeatScale)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "TIEMPO EXACTO JUNTOS",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 4 Main Clock Tiles (Days, Hours, Minutes, Seconds)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("counter_tiles_row"),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CounterUnitTile(
                value = timePassed.totalDays.toString(),
                label = "DÍAS",
                modifier = Modifier.weight(1f)
            )
            CounterUnitTile(
                value = String.format(Locale.US, "%02d", timePassed.hours),
                label = "HORAS",
                modifier = Modifier.weight(1f)
            )
            CounterUnitTile(
                value = String.format(Locale.US, "%02d", timePassed.minutes),
                label = "MIN",
                modifier = Modifier.weight(1f)
            )
            CounterUnitTile(
                value = String.format(Locale.US, "%02d", timePassed.seconds),
                label = "SEG",
                modifier = Modifier.weight(1f),
                isAccent = true
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Breakdown Summary Pill (Years, Months, Days)
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${timePassed.years} Años • ${timePassed.months} Meses • ${timePassed.days} Días",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Heartbeats & Next Anniversary Statistics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Heartbeats Card
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFFFD9E2), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color(0xFFE11D48),
                            modifier = Modifier
                                .size(20.dp)
                                .scale(heartbeatScale)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = NumberFormat.getNumberInstance(Locale("es", "ES"))
                            .format(timePassed.estimatedHeartbeats),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Latidos por ti",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Next 10th Milestone Card
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFFFD8EC), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = Color(0xFFC026D3),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (timePassed.isAnniversaryDay) "¡HOY!" else "${timePassed.daysUntilNextTenth}d ${timePassed.hoursUntilNextTenth}h",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Próximo día 10",
                        fontSize = 11.sp,
                        color = Color(0xFFC026D3)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Featured Monthly Love Letter Preview Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("featured_letter_card"),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Mail,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "CARTA DEL ANIVERSARIO",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = currentLetter.dateLabel,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = currentLetter.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "\"${currentLetter.romanticQuote}\"",
                    fontSize = 13.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { onNavigateToLetters(timePassed.monthsTogether) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("read_monthly_letter_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Leer Carta Completa 💌",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun CounterUnitTile(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    isAccent: Boolean = false
) {
    Card(
        modifier = modifier.height(86.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isAccent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = if (!isAccent) BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer) else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                fontSize = if (value.length > 3) 20.sp else 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (isAccent) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                color = if (isAccent) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f) else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
