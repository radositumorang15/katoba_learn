package id.kotoba.app.view.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

private data class ConfettiParticle(
    val initialX: Float,
    val speed: Float,
    val size: Float,
    val color: Color
)

@Composable
fun ConfettiView(
    modifier: Modifier = Modifier
) {
    val particles = remember {
        val colors = listOf(
            Color(0xFF0F755E),
            Color(0xFF5C4EE5),
            Color(0xFFF59E0B),
            Color(0xFFEF4444),
            Color(0xFF10B981),
            Color(0xFFEC4899)
        )
        List(45) {
            ConfettiParticle(
                initialX = Random.nextFloat(),
                speed = 0.5f + Random.nextFloat() * 1.5f,
                size = 8f + Random.nextFloat() * 12f,
                color = colors[Random.nextInt(colors.size)]
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        particles.forEach { p ->
            val y = ((progress * p.speed * canvasHeight) % (canvasHeight + 100)) - 50
            val x = (p.initialX * canvasWidth) + (kotlin.math.sin(progress * 6f + p.initialX * 10f) * 30f)
            drawRect(
                color = p.color,
                topLeft = Offset(x, y),
                size = Size(p.size, p.size * 1.6f)
            )
        }
    }
}
