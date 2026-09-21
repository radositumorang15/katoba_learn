package id.kotoba.app.view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kotoba.app.controller.QuizController
import id.kotoba.app.view.components.ConfettiView
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.DeepPurple
import id.kotoba.app.view.theme.ErrorRed
import id.kotoba.app.view.theme.PastelCoral
import id.kotoba.app.view.theme.PastelGreen
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.ShapeBadgePill
import id.kotoba.app.view.theme.ShapeCardMedium
import id.kotoba.app.view.theme.ShapeHeroLarge
import id.kotoba.app.view.theme.SoftPurple
import id.kotoba.app.view.theme.SoftYellow
import id.kotoba.app.view.theme.SuccessGreen
import id.kotoba.app.view.theme.SurfaceCard
import id.kotoba.app.view.theme.TextPrimary
import id.kotoba.app.view.theme.TextSecondary

@Composable
fun QuizScreen(
    controller: QuizController
) {
    val currentIndex by controller.currentIndex.collectAsState()
    val selectedOption by controller.selectedOptionIndex.collectAsState()
    val isSubmitted by controller.isAnswerSubmitted.collectAsState()
    val isCompleted by controller.isQuizCompleted.collectAsState()
    val result by controller.quizResult.collectAsState()
    val showConfetti by controller.showConfetti.collectAsState()
    val question = controller.currentQuestion
    val questions = controller.questions

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (showConfetti) {
            ConfettiView()
        }

        if (isCompleted && result != null) {
            // Result Screen
            QuizResultView(
                result = result!!,
                onRestart = { controller.restartQuiz() },
                onContinue = { controller.continueToNextLevel() },
                onBack = { controller.goBack() }
            )
        } else if (question != null) {
            // Active Quiz Flow
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Header Bar
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
                            Text(text = "✕", fontSize = 16.sp, color = TextPrimary)
                        }

                        Text(
                            text = "⚔️ Boss Battle: Soal ${currentIndex + 1}/${questions.size}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryGreen
                        )

                        Box(
                            modifier = Modifier
                                .clip(ShapeBadgePill)
                                .background(SoftPurple)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Lulus: ≥80%",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepPurple
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    val progress = (currentIndex + 1).toFloat() / questions.size.toFloat()
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(ShapeBadgePill),
                        color = PrimaryGreen,
                        trackColor = Color(0xFFE2E8F0)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Audio & Visual Prompt Card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(ShapeHeroLarge)
                            .background(PrimarySurface)
                            .border(1.dp, PrimaryGreen.copy(alpha = 0.3f), ShapeHeroLarge)
                            .padding(20.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Audio Speaker CTA
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryGreen)
                                    .clickable { controller.playQuestionAudio() },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "🔊",
                                    fontSize = 28.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Ketuk untuk dengar suara",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryGreen
                            )

                            if (question.visualCharacter != null && question.visualCharacter != "?") {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = question.visualCharacter,
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = question.questionText,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 4 Multiple Choice Options
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        question.options.forEachIndexed { index, option ->
                            val isSelected = selectedOption == index
                            val isCorrect = index == question.correctOptionIndex

                            val bgColor = when {
                                isSubmitted && isCorrect -> PastelGreen
                                isSubmitted && isSelected && !isCorrect -> PastelCoral
                                isSelected -> SoftPurple
                                else -> SurfaceCard
                            }

                            val borderColor = when {
                                isSubmitted && isCorrect -> SuccessGreen
                                isSubmitted && isSelected && !isCorrect -> ErrorRed
                                isSelected -> DeepPurple
                                else -> BorderOutline
                            }

                            val textColor = when {
                                isSubmitted && isCorrect -> SuccessGreen
                                isSubmitted && isSelected && !isCorrect -> ErrorRed
                                isSelected -> DeepPurple
                                else -> TextPrimary
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(ShapeCardMedium)
                                    .background(bgColor)
                                    .border(if (isSelected || (isSubmitted && isCorrect)) 2.dp else 1.dp, borderColor, ShapeCardMedium)
                                    .clickable { controller.selectOption(index) }
                                    .padding(horizontal = 18.dp, vertical = 14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${('A' + index)}.  $option",
                                        fontSize = 15.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = textColor
                                    )

                                    if (isSubmitted) {
                                        Text(
                                            text = if (isCorrect) "✓" else if (isSelected) "✕" else "",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isCorrect) SuccessGreen else ErrorRed
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Explanation card after submission
                    AnimatedVisibility(visible = isSubmitted, enter = fadeIn()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .clip(ShapeCardMedium)
                                .background(Color(0xFFF8FAFC))
                                .border(1.dp, BorderOutline, ShapeCardMedium)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "💡 Pembahasan Sensei:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = question.explanation,
                                fontSize = 13.sp,
                                color = TextSecondary,
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "🎬 Referensi Anime: ${question.animeContext}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryGreen
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bottom Action Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ShapeBadgePill)
                        .background(
                            if (selectedOption != null) PrimaryGreen else Color(0xFFCBD5E1)
                        )
                        .clickable(enabled = selectedOption != null) {
                            if (!isSubmitted) {
                                controller.submitAnswer()
                            } else {
                                controller.nextQuestion()
                            }
                        }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (!isSubmitted) "Periksa Jawaban" else if (currentIndex < questions.size - 1) "Soal Berikutnya  ➔" else "Lihat Hasil Boss Battle",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizResultView(
    result: id.kotoba.app.controller.QuizResult,
    onRestart: () -> Unit,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Mascot Emoji
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(if (result.isPassed) PastelGreen else PastelCoral),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (result.isPassed) "🏆" else "💪",
                    fontSize = 54.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (result.isPassed) "Kemenangan Epik!" else "Belum Menyerah!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (result.isPassed)
                    "Luar biasa! Skor kamu mencukupi syarat kelulusan level (≥80%). Level berikutnya terbuka!"
                else
                    "Kamu menjawab ${result.correctCount} dari ${result.totalQuestions} soal dengan benar (${result.scorePercentage}%). Ulangi kuis untuk membuka level baru.",
                fontSize = 14.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Score Card & Rewards
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ShapeHeroLarge)
                    .background(PrimarySurface)
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Skor Akhir", fontSize = 12.sp, color = TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${result.scorePercentage}%",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (result.isPassed) PrimaryGreen else ErrorRed
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "XP Diperoleh", fontSize = 12.sp, color = TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "+${result.earnedXp} XP",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepPurple
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Koin Hadiah", fontSize = 12.sp, color = TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "+${result.earnedCoins} 🪙",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF59E0B)
                        )
                    }
                }
            }
        }

        // Action Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (result.isPassed) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ShapeBadgePill)
                        .background(PrimaryGreen)
                        .clickable { onContinue() }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Lanjut ke Roadmap Level  ➔",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ShapeBadgePill)
                        .background(PrimaryGreen)
                        .clickable { onRestart() }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ulangi Boss Battle  🔄",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ShapeBadgePill)
                    .border(1.dp, BorderOutline, ShapeBadgePill)
                    .clickable { onBack() }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Kembali ke Menu Utama",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
