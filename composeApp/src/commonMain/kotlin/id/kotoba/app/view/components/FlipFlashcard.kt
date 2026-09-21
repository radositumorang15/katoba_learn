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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

import id.kotoba.app.model.repository.DynamicSentenceExample
import androidx.compose.material3.CircularProgressIndicator

@Composable
fun FlipFlashcard(
    kana: KanaItem,
    isFlipped: Boolean,
    onFlip: () -> Unit,
    onPlayAudio: () -> Unit,
    modifier: Modifier = Modifier,
    furiganaEnabled: Boolean = true,
    aiExample: DynamicSentenceExample? = null,
    isLoadingAiExample: Boolean = false,
    onGenerateAiExample: () -> Unit = {},
    onPlayAiExampleAudio: (String) -> Unit = {}
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 390.dp, max = 480.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 14f * density
            }
            .clip(ShapeHeroLarge)
            .background(SurfaceCard)
            .border(2.dp, BorderOutline, ShapeHeroLarge)
            .clickable { onFlip() }
            .padding(20.dp)
    ) {
        if (rotation <= 90f) {
            // Front Face (Kana character)
            CardFrontFace(
                kana = kana,
                onPlayAudio = onPlayAudio,
                furiganaEnabled = furiganaEnabled
            )
        } else {
            // Back Face (Romaji & Anime context & AI Example)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f }
            ) {
                CardBackFace(
                    kana = kana,
                    onPlayAudio = onPlayAudio,
                    aiExample = aiExample,
                    isLoadingAiExample = isLoadingAiExample,
                    onGenerateAiExample = onGenerateAiExample,
                    onPlayAiExampleAudio = onPlayAiExampleAudio
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
                    .border(1.dp, PrimaryGreen.copy(alpha = 0.25f), ShapeBadgePill)
                    .padding(horizontal = 12.dp, vertical = 5.dp)
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
                Spacer(modifier = Modifier.height(4.dp))
            }
            Text(
                text = kana.character,
                fontSize = if (!kana.furigana.isNullOrBlank()) 68.sp else 80.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            if (kana.mnemonic.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
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
    onPlayAudio: () -> Unit,
    aiExample: DynamicSentenceExample? = null,
    isLoadingAiExample: Boolean = false,
    onGenerateAiExample: () -> Unit = {},
    onPlayAiExampleAudio: (String) -> Unit = {}
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
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(SoftPurple)
                    .clickable { onPlayAudio() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🔊",
                    fontSize = 16.sp
                )
            }
        }

        // Center: Romaji & Meaning & AI Example
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = kana.romaji,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .clip(ShapeBadgePill)
                    .background(PrimarySurface)
                    .border(1.dp, PrimaryGreen.copy(alpha = 0.2f), ShapeBadgePill)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "🎬 ${kana.animeContext}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = kana.meaning,
                fontSize = 13.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Dynamic Gemini AI Example Block
            if (aiExample != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(14.dp))
                        .background(Color(0xFFF0FDF4))
                        .border(1.dp, Color(0xFFBBF7D0), androidx.compose.foundation.shape.RoundedCornerShape(14.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(ShapeBadgePill)
                                        .background(Color.White)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "✨ Gemini AI",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryGreen
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Contoh Kalimat",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = PrimaryGreen
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(SoftPurple)
                                    .clickable { onPlayAiExampleAudio(aiExample.japanese) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "🔊", fontSize = 12.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = aiExample.japanese,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = aiExample.romaji,
                            fontSize = 12.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            color = PrimaryGreen
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = aiExample.indonesian,
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }
            } else if (isLoadingAiExample) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(ShapeBadgePill)
                        .background(Color(0xFFFEF3C7))
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(14.dp),
                        color = Color(0xFFD97706),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Gemini sedang membuat contoh...",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF92400E)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .clip(ShapeBadgePill)
                        .background(PrimarySurface)
                        .border(1.5.dp, PrimaryGreen.copy(alpha = 0.35f), ShapeBadgePill)
                        .clickable { onGenerateAiExample() }
                        .padding(horizontal = 16.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = "✨ Buat Contoh Kalimat (Gemini AI)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                }
            }
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
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
            )
        }
    }
}
