package com.example.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.random.Random

data class TapHeart(
    val x: Float,
    val y: Float,
    val size: Float,
    val alpha: Float,
    val color: Color
)

@Composable
fun RomanticBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "floating_hearts")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    // Tap-generated hearts
    val tapHearts = remember { mutableStateListOf<TapHeart>() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val colors = listOf(
                        Color(0xFFFB7185),
                        Color(0xFFFDA4AF),
                        Color(0xFFE11D48),
                        Color(0xFFF472B6)
                    )
                    for (i in 0..3) {
                        tapHearts.add(
                            TapHeart(
                                x = offset.x + Random.nextInt(-40, 40),
                                y = offset.y + Random.nextInt(-30, 30),
                                size = Random.nextFloat() * 16f + 16f,
                                alpha = 0.85f,
                                color = colors.random()
                            )
                        )
                    }
                    if (tapHearts.size > 24) {
                        tapHearts.removeRange(0, tapHearts.size - 24)
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Render floating ambient hearts
            val heartCount = 14
            for (i in 0 until heartCount) {
                val seed = i * 1337
                val randomX = (seed % 1000) / 1000f * width
                val speed = 0.6f + (i % 5) * 0.15f
                val yOffset = ((phase * speed + (i.toFloat() / heartCount)) % 1f)
                val currentY = height - (yOffset * (height + 100))

                val heartSize = 16f + (i % 4) * 8f
                val alpha = (0.25f + (i % 3) * 0.15f).coerceIn(0.1f, 0.5f)
                val heartColor = if (i % 2 == 0) Color(0xFFFDA4AF) else Color(0xFFFB7185)

                drawHeart(
                    center = Offset(randomX, currentY),
                    size = heartSize,
                    color = heartColor.copy(alpha = alpha)
                )
            }

            // Draw tap hearts
            for (heart in tapHearts) {
                drawHeart(
                    center = Offset(heart.x, heart.y),
                    size = heart.size,
                    color = heart.color.copy(alpha = heart.alpha)
                )
            }
        }

        content()
    }
}

private fun DrawScope.drawHeart(center: Offset, size: Float, color: Color) {
    val path = Path().apply {
        val w = size
        val h = size
        val left = center.x - w / 2
        val top = center.y - h / 2

        moveTo(center.x, center.y + h * 0.45f)
        cubicTo(
            left - w * 0.15f, center.y - h * 0.1f,
            left + w * 0.1f, top - h * 0.1f,
            center.x, center.y - h * 0.05f
        )
        cubicTo(
            left + w * 0.9f, top - h * 0.1f,
            left + w * 1.15f, center.y - h * 0.1f,
            center.x, center.y + h * 0.45f
        )
        close()
    }
    drawPath(path = path, color = color)
}
