package id.kotoba.app.view.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kotoba.app.model.KanaItem
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.PastelGreen
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.ShapeBadgePill
import id.kotoba.app.view.theme.ShapeHeroLarge
import id.kotoba.app.view.theme.SoftPurple
import id.kotoba.app.view.theme.SurfaceCard
import id.kotoba.app.view.theme.TextPrimary
import id.kotoba.app.view.theme.TextSecondary

@Composable
fun FlipFlashcard(
    kana: KanaItem,
    isFlipped: Boolean,
    onFlip: () -> Unit,
    onPlayAudio: () -> Unit,
    modifier: Modifier = Modifier,
    furiganaEnabled: Boolean = true
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(340.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 14f * density
            }
            .clip(ShapeHeroLarge)
            .background(SurfaceCard)
            .border(2.dp, BorderOutline, ShapeHeroLarge)
            .clickable { onFlip() }
            .padding(24.dp)
    ) {
        if (rotation <= 90f) {
            // Front Face (Kana character)
            CardFrontFace(
                kana = kana,
                onPlayAudio = onPlayAudio,
                furiganaEnabled = furiganaEnabled
            )
        } else {
            // Back Face (Romaji & Anime context)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f }
            ) {
                CardBackFace(
                    kana = kana,
                    onPlayAudio = onPlayAudio
                )
            }
        }
    }
}

@Composable
private fun CardFrontFace(
    kana: KanaItem,
    onPlayAudio: () -> Unit,
    furiganaEnabled: Boolean = true
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(ShapeBadgePill)
                    .background(PrimarySurface)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = kana.type.name.replace("_", " "),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryGreen
                )
            }

            // Audio button
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(SoftPurple)
                    .clickable { onPlayAudio() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🔊",
                    fontSize = 18.sp
                )
            }
        }

        // Center: Furigana + Kana Character
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (furiganaEnabled && !kana.furigana.isNullOrBlank()) {
                Text(
                    text = kana.furigana,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryGreen,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
            Text(
                text = kana.character,
                fontSize = if (!kana.furigana.isNullOrBlank()) 70.sp else 84.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            if (kana.mnemonic.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "💡 ${kana.mnemonic}",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
            }
        }

        // Bottom flip hint
        Box(
            modifier = Modifier
                .clip(ShapeBadgePill)
                .background(PastelGreen)
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = "🔄 Ketuk kartu untuk melihat arti & romaji",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryGreen
            )
        }
    }
}

@Composable
private fun CardBackFace(
    kana: KanaItem,
    onPlayAudio: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Romaji & Pop-Culture",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary
            )

            // Audio button
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(SoftPurple)
                    .clickable { onPlayAudio() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🔊",
                    fontSize = 18.sp
                )
            }
        }

        // Center: Romaji & Meaning
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Text(
                text = kana.romaji,
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .clip(ShapeHeroLarge)
                    .background(PrimarySurface)
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "🎬 ${kana.animeContext}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = kana.meaning,
                fontSize = 13.sp,
                color = TextSecondary
            )
        }

        // Bottom flip back hint
        Box(
            modifier = Modifier
                .clip(ShapeBadgePill)
                .background(Color(0xFFF1F5F9))
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = "🔄 Ketuk kartu untuk kembali ke huruf",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
            )
        }
    }
}
