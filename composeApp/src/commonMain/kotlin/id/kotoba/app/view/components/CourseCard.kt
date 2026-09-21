package id.kotoba.app.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kotoba.app.model.Level
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.DeepPurple
import id.kotoba.app.view.theme.LockedBackground
import id.kotoba.app.view.theme.LockedGray
import id.kotoba.app.view.theme.PastelBlue
import id.kotoba.app.view.theme.PastelCoral
import id.kotoba.app.view.theme.PastelGreen
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.ShapeBadgePill
import id.kotoba.app.view.theme.ShapeCardMedium
import id.kotoba.app.view.theme.ShapeImageSmall
import id.kotoba.app.view.theme.SoftPurple
import id.kotoba.app.view.theme.SoftYellow
import id.kotoba.app.view.theme.SuccessGreen
import id.kotoba.app.view.theme.SurfaceCard
import id.kotoba.app.view.theme.TextPrimary
import id.kotoba.app.view.theme.TextSecondary

@Composable
fun CourseCard(
    level: Level,
    modifier: Modifier = Modifier,
    onStartLearning: () -> Unit,
    onStartQuiz: () -> Unit
) {
    val pastelBannerBg = when (level.levelNumber) {
        1 -> PastelGreen
        2 -> PastelBlue
        3 -> SoftPurple
        4 -> SoftYellow
        5 -> PastelCoral
        else -> PrimarySurface
    }

    val iconSymbol = when (level.levelNumber) {
        1 -> "あ"
        2 -> "が"
        3 -> "きゃ"
        4 -> "⚡"
        5 -> "ア"
        else -> "🌸"
    }

    Box(
        modifier = modifier
            .clip(ShapeCardMedium)
            .background(if (level.isUnlocked) SurfaceCard else LockedBackground)
            .border(1.dp, BorderOutline, ShapeCardMedium)
            .padding(16.dp)
    ) {
        Column {
            // Upper visual banner (16dp rounded corner)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(ShapeImageSmall)
                    .background(if (level.isUnlocked) pastelBannerBg else Color(0xFFE2E8F0)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        // Category Pill tag
                        Box(
                            modifier = Modifier
                                .clip(ShapeBadgePill)
                                .background(Color.White.copy(alpha = 0.85f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = level.category,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (level.isUnlocked) PrimaryGreen else LockedGray
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Level ${level.levelNumber}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    // Mascot / Large Character
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.9f))
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (level.isUnlocked) iconSymbol else "🔒",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (level.isUnlocked) PrimaryGreen else LockedGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Title & Subtitle
            Text(
                text = level.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = level.subtitle,
                fontSize = 12.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Metadata row (lessons count, XP)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "📚 ${level.totalLessons} Pelajaran",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "⭐ +${level.xpReward} XP",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepPurple
                    )
                }

                if (level.isCompleted) {
                    Box(
                        modifier = Modifier
                            .clip(ShapeBadgePill)
                            .background(PastelGreen)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "✓ Selesai",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SuccessGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Full-width Pill CTA button
            if (level.isUnlocked) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Start Learning CTA
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(ShapeBadgePill)
                            .background(PrimaryGreen)
                            .clickable { onStartLearning() }
                            .padding(vertical = 11.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Belajar Flashcard",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Boss Battle Quiz CTA
                    Box(
                        modifier = Modifier
                            .clip(ShapeBadgePill)
                            .background(SoftYellow)
                            .border(1.dp, Color(0xFFFBBF24), ShapeBadgePill)
                            .clickable { onStartQuiz() }
                            .padding(horizontal = 14.dp, vertical = 11.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "⚔️ Kuis",
                            color = Color(0xFFB45309),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ShapeBadgePill)
                        .background(Color(0xFFE2E8F0))
                        .padding(vertical = 11.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🔒 Selesaikan Level Sebelumnya",
                        color = LockedGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
