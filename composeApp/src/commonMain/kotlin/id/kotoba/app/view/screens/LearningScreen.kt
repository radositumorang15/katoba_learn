package id.kotoba.app.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
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
import id.kotoba.app.controller.LearningController
import id.kotoba.app.view.components.FlipFlashcard
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.PastelCoral
import id.kotoba.app.view.theme.PastelGreen
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.ShapeBadgePill
import id.kotoba.app.view.theme.SoftYellow
import id.kotoba.app.view.theme.TextPrimary
import id.kotoba.app.view.theme.TextSecondary

import id.kotoba.app.view.theme.DeepPurple
import id.kotoba.app.view.theme.SoftPurple

@Composable
fun LearningScreen(
    controller: LearningController
) {
    val currentIndex by controller.currentIndex.collectAsState()
    val isFlipped by controller.isFlipped.collectAsState()
    val masteredIds by controller.masteredKanaIds.collectAsState()
    val furiganaEnabled by controller.furiganaEnabled.collectAsState()
    val showCoachMark by controller.showCoachMark.collectAsState()
    val aiExample by controller.currentAiExample.collectAsState()
    val isLoadingAiExample by controller.isLoadingAiExample.collectAsState()
    val currentKana = controller.currentKana
    val kanaList = controller.kanaList

    val progress = if (kanaList.isNotEmpty()) {
        (currentIndex + 1).toFloat() / kanaList.size.toFloat()
    } else 0f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 680.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
        Column {
            // Top Navigation & Progress Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF1F5F9))
                        .clickable { controller.goBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "←", fontSize = 20.sp, color = TextPrimary)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = controller.level?.title ?: "Flashcard Kana",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Kartu ${currentIndex + 1} dari ${kanaList.size}",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    // AI Conversation Partner Button (Fase D / General AI)
                    Box(
                        modifier = Modifier
                            .clip(ShapeBadgePill)
                            .background(SoftPurple)
                            .clickable { controller.goToAiConversation() }
                            .padding(horizontal = 9.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "💬 AI",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepPurple
                        )
                    }

                    // Furigana Toggle Pill
                    Box(
                        modifier = Modifier
                            .clip(ShapeBadgePill)
                            .background(if (furiganaEnabled) PrimarySurface else Color(0xFFF1F5F9))
                            .clickable { controller.toggleFurigana() }
                            .padding(horizontal = 9.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (furiganaEnabled) "ルビ ON" else "ルビ OFF",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (furiganaEnabled) PrimaryGreen else TextSecondary
                        )
                    }

                    // Quiz Button
                    Box(
                        modifier = Modifier
                            .clip(ShapeBadgePill)
                            .background(SoftYellow)
                            .clickable { controller.goToQuiz() }
                            .padding(horizontal = 9.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "⚔️ Kuis",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB45309)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(ShapeBadgePill),
                color = PrimaryGreen,
                trackColor = Color(0xFFE2E8F0)
            )

            // Coach Mark Interactive Tooltip (PRD Fase 1.5)
            if (showCoachMark) {
                Spacer(modifier = Modifier.height(14.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                        .background(Color(0xFFFEF3C7))
                        .border(1.dp, Color(0xFFFDE68A), androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Row(modifier = Modifier.weight(1f)) {
                            Text(text = "💡", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Panduan Onboarding (~5 Menit / Modul)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF92400E)
                                )
                                Text(
                                    text = "Tap kartu untuk mendengarkan audio asli. Tandai 'Hafal' atau 'Ulangi' untuk input jadwal Spaced Repetition (SRS).",
                                    fontSize = 11.sp,
                                    color = Color(0xFF78350F),
                                    lineHeight = 16.sp
                                )
                            }
                        }
                        Text(
                            text = "✕",
                            fontSize = 14.sp,
                            color = Color(0xFF92400E),
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { controller.dismissCoachMark() }
                                .padding(4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Kana quick selector row
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(kanaList) { idx, item ->
                    val isSelected = idx == currentIndex
                    val isMastered = masteredIds.contains(item.id)
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isSelected -> PrimaryGreen
                                    isMastered -> PastelGreen
                                    else -> Color(0xFFF1F5F9)
                                }
                            )
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) PrimaryGreen else BorderOutline,
                                shape = CircleShape
                            )
                            .clickable { controller.selectCardIndex(idx) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.character,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Interactive 3D Flip Flashcard
            if (currentKana != null) {
                FlipFlashcard(
                    kana = currentKana,
                    isFlipped = isFlipped,
                    onFlip = { controller.flipCard() },
                    onPlayAudio = { controller.playCurrentAudio() },
                    furiganaEnabled = furiganaEnabled,
                    aiExample = aiExample,
                    isLoadingAiExample = isLoadingAiExample,
                    onGenerateAiExample = { controller.generateAiExample() },
                    onPlayAiExampleAudio = { controller.playAiExampleAudio(it) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons: "Belum Hafal" & "Sudah Hafal"
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Repeat button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(ShapeBadgePill)
                        .background(PastelCoral)
                        .clickable { controller.markRepeat() }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🔁 Belum Hafal",
                        color = Color(0xFFDC2626),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Mastered button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(ShapeBadgePill)
                        .background(PrimaryGreen)
                        .clickable { controller.markMastered() }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓ Sudah Hafal",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Next / Previous Navigation Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (currentIndex > 0) "◀ Sebelumnya" else "",
                    fontSize = 13.sp,
                    color = PrimaryGreen,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { controller.previousCard() }
                )

                Text(
                    text = if (currentIndex < kanaList.size - 1) "Selanjutnya ▶" else "Selesai ➔ Ikuti Kuis",
                    fontSize = 13.sp,
                    color = PrimaryGreen,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        if (currentIndex < kanaList.size - 1) controller.nextCard() else controller.goToQuiz()
                    }
                )
            }
        }
    }
}
}
