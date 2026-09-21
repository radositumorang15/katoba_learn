package id.kotoba.app.view.screens

import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kotoba.app.view.theme.PastelBlue
import id.kotoba.app.view.theme.PastelCoral
import id.kotoba.app.view.theme.PastelGreen
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.ShapeBadgePill
import id.kotoba.app.view.theme.SoftPurple
import id.kotoba.app.view.theme.TextPrimary
import id.kotoba.app.view.theme.TextSecondary

private data class OnboardingSlide(
    val title: String,
    val description: String,
    val emoji: String,
    val bgCircle: Color
)

@Composable
fun OnboardingScreen(
    onStartAdventure: () -> Unit
) {
    val slides = listOf(
        OnboardingSlide(
            title = "Belajar Bahasa Jepang dari Anime Favoritmu!",
            description = "Kenali bunyi 'Arigatou', 'Sugoi', dan 'Baka' dalam aksara aslinya tanpa rasa terintimidasi.",
            emoji = "🎌",
            bgCircle = PastelGreen
        ),
        OnboardingSlide(
            title = "Dengar, Tebak, dan Hafalkan Kana tanpa Bosan",
            description = "Metode audio-first dengan pengucapan Native TTS dan flashcard 3D flip interaktif.",
            emoji = "🎧",
            bgCircle = PastelBlue
        ),
        OnboardingSlide(
            title = "Mulai Petualanganmu Sekarang!",
            description = "Taklukkan Boss Battle kuis, raih XP, dan buka level baru hingga membaca manga tanpa subtitle!",
            emoji = "⚔️",
            bgCircle = SoftPurple
        )
    )

    var currentSlideIndex by remember { mutableStateOf(0) }
    val slide = slides[currentSlideIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Skip Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            if (currentSlideIndex < slides.size - 1) {
                Text(
                    text = "Lewati",
                    fontSize = 14.sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onStartAdventure() }
                )
            } else {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        // Center Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .background(slide.bgCircle),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = slide.emoji,
                    fontSize = 72.sp
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = slide.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = slide.description,
                fontSize = 14.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Indicators
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                slides.indices.forEach { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == currentSlideIndex) 24.dp else 8.dp, 8.dp)
                            .clip(CircleShape)
                            .background(if (index == currentSlideIndex) PrimaryGreen else Color(0xFFCBD5E1))
                    )
                }
            }
        }

        // Bottom CTA
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ShapeBadgePill)
                    .background(PrimaryGreen)
                    .clickable {
                        if (currentSlideIndex < slides.size - 1) {
                            currentSlideIndex++
                        } else {
                            onStartAdventure()
                        }
                    }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (currentSlideIndex < slides.size - 1) "Selanjutnya  ➔" else "Mulai Petualanganmu!",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
