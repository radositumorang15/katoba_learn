package id.kotoba.app.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kotoba.app.Res
import id.kotoba.app.view.theme.PrimaryGreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap

@OptIn(ExperimentalResourceApi::class)
@Composable
fun KatobaLogo(
    size: Dp = 120.dp,
    cornerRadius: Dp = 28.dp,
    modifier: Modifier = Modifier
) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var loadAttempted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val bytes = Res.readBytes("drawable/katoba.png")
            imageBitmap = bytes.decodeToImageBitmap()
        } catch (t: Throwable) {
            // Graceful fallback if resource is missing or inaccessible on native runtime
            println("KatobaLogo: using styled fallback logo (${t.message})")
        } finally {
            loadAttempted = true
        }
    }

    val currentBitmap = imageBitmap
    if (currentBitmap != null) {
        Image(
            bitmap = currentBitmap,
            contentDescription = "Katoba Logo",
            modifier = modifier
                .size(size)
                .clip(RoundedCornerShape(cornerRadius))
        )
    } else {
        // High-end fallback logo: modern Japanese aesthetic card
        Box(
            modifier = modifier
                .size(size)
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            PrimaryGreen,
                            Color(0xFF2E7D32)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "言",
                color = Color.White,
                fontSize = (size.value * 0.45f).sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}
