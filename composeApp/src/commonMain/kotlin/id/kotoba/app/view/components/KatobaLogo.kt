package id.kotoba.app.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kotoba.app.view.theme.PrimaryGreen

/**
 * 100% native Compose Canvas logo — ZERO file I/O, ZERO async operations, ZERO resource loading.
 * Safe to render on first frame, even before any coroutine or IO dispatcher is initialized.
 */
@Composable
fun KatobaLogo(
    size: Dp = 120.dp,
    cornerRadius: Dp = 28.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF4CAF50),
                        Color(0xFF1B5E20)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Decorative concentric circles (Japanese aesthetic)
        Canvas(modifier = Modifier.size(size * 0.85f)) {
            val center = Offset(this.size.width / 2f, this.size.height / 2f)
            val outerR = this.size.minDimension / 2f * 0.9f
            val innerR = outerR * 0.65f

            drawCircle(
                color = Color.White.copy(alpha = 0.15f),
                radius = outerR,
                center = center,
                style = Stroke(width = 3.dp.toPx())
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.25f),
                radius = innerR,
                center = center,
                style = Stroke(width = 2.dp.toPx())
            )
        }

        // ASCII letter to avoid CJK font-cache lookup on first render (Sideloadly sandbox blocks vfs.disk-space)
        Text(
            text = "K",
            color = Color.White,
            fontSize = (size.value * 0.45f).sp,
            fontWeight = FontWeight.Black
        )
    }
}
