package id.kotoba.app.view.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kotoba.app.controller.RoadmapController
import id.kotoba.app.model.Level
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.DeepPurple
import id.kotoba.app.view.theme.PastelCoral
import id.kotoba.app.view.theme.PastelGreen
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.ShapeBadgePill
import id.kotoba.app.view.theme.ShapeCardMedium
import id.kotoba.app.view.theme.SoftYellow
import id.kotoba.app.view.theme.SuccessGreen
import id.kotoba.app.view.theme.SurfaceCard
import id.kotoba.app.view.theme.TextPrimary
import id.kotoba.app.view.theme.TextSecondary

@Composable
fun RoadmapScreen(
    controller: RoadmapController,
    modifier: Modifier = Modifier
) {
    val levels by controller.levels.collectAsState()
    val notice by controller.lockedNotice.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            item {
                Text(
                    text = "Peta Level Petualangan",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Taklukkan setiap level untuk membuka materi selanjutnya!",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (notice != null) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(ShapeCardMedium)
                            .background(PastelCoral)
                            .clickable { controller.dismissNotice() }
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = notice ?: "",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFDC2626),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "✕", fontSize = 14.sp, color = Color(0xFFDC2626))
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }

            itemsIndexed(levels) { index, level ->
                val isNewPhase = index == 0 || levels[index].category != levels[index - 1].category
                if (isNewPhase) {
                    PhaseHeaderCard(category = level.category)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                RoadmapLevelItem(
                    level = level,
                    isLast = index == levels.size - 1,
                    onClick = { controller.onLevelClick(level) },
                    onStartQuiz = { controller.onStartQuiz(level.id) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
private fun RoadmapLevelItem(
    level: Level,
    isLast: Boolean,
    onClick: () -> Unit,
    onStartQuiz: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        // Vertical step node line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(48.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            level.isCompleted -> PastelGreen
                            level.isUnlocked -> PrimarySurface
                            else -> Color(0xFFF1F5F9)
                        }
                    )
                    .border(
                        width = 2.dp,
                        color = when {
                            level.isCompleted -> SuccessGreen
                            level.isUnlocked -> PrimaryGreen
                            else -> Color(0xFFCBD5E1)
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when {
                        level.isCompleted -> "✓"
                        level.isUnlocked -> "${level.levelNumber}"
                        else -> "🔒"
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        level.isCompleted -> SuccessGreen
                        level.isUnlocked -> PrimaryGreen
                        else -> Color(0xFF94A3B8)
                    }
                )
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .height(76.dp)
                        .background(if (level.isCompleted) SuccessGreen else Color(0xFFE2E8F0))
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Level Details Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (!isLast) 16.dp else 0.dp)
                .clip(ShapeCardMedium)
                .background(if (level.isUnlocked) SurfaceCard else Color(0xFFF8FAFC))
                .border(1.dp, BorderOutline, ShapeCardMedium)
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(ShapeBadgePill)
                            .background(if (level.isUnlocked) PrimarySurface else Color(0xFFE2E8F0))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = level.category,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (level.isUnlocked) PrimaryGreen else Color(0xFF94A3B8)
                        )
                    }

                    Text(
                        text = "+${level.xpReward} XP",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepPurple
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = level.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = level.description,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )

                if (level.isUnlocked) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(ShapeBadgePill)
                                .background(PrimaryGreen)
                                .clickable { onClick() }
                                .padding(horizontal = 14.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Buka Modul",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(ShapeBadgePill)
                                .background(SoftYellow)
                                .border(1.dp, Color(0xFFFBBF24), ShapeBadgePill)
                                .clickable { onStartQuiz() }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "⚔️ Kuis",
                                color = Color(0xFFB45309),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PhaseHeaderCard(category: String) {
    val icon = when {
        category.contains("Fase A", ignoreCase = true) -> "🌱"
        category.contains("Fase B", ignoreCase = true) -> "📺"
        category.contains("Fase C", ignoreCase = true) -> "⛩️"
        else -> "🗣️"
    }

    val badgeColor = when {
        category.contains("Fase A", ignoreCase = true) -> Color(0xFFE5F4F0)
        category.contains("Fase B", ignoreCase = true) -> Color(0xFFE8E5FE)
        category.contains("Fase C", ignoreCase = true) -> Color(0xFFFDF1D5)
        else -> Color(0xFFE0F2FE)
    }

    val textColor = when {
        category.contains("Fase A", ignoreCase = true) -> Color(0xFF0F755E)
        category.contains("Fase B", ignoreCase = true) -> Color(0xFF5C4EE5)
        category.contains("Fase C", ignoreCase = true) -> Color(0xFFB45309)
        else -> Color(0xFF0369A1)
    }

    val desc = when {
        category.contains("Fase A", ignoreCase = true) -> "Pondasi aksara dasar: Seion, Dakuon, & Sokuon"
        category.contains("Fase B", ignoreCase = true) -> "Kosakata anime populer & aksara serapan Katakana"
        category.contains("Fase C", ignoreCase = true) -> "Kanji angka, sehari-hari, dan pola kalimat (JLPT N5)"
        else -> "Percakapan situasional, konbini, & listening mandiri"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp, bottom = 4.dp)
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(14.dp))
            .background(badgeColor)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = category,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = desc,
                    fontSize = 11.sp,
                    color = textColor.copy(alpha = 0.85f)
                )
            }
        }
    }
}

