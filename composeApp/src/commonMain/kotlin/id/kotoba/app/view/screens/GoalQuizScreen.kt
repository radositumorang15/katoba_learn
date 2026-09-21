package id.kotoba.app.view.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import id.kotoba.app.controller.GoalQuizController
import id.kotoba.app.model.CurrentKnowledge
import id.kotoba.app.model.PrimaryGoal
import id.kotoba.app.view.components.ConfettiView
import id.kotoba.app.view.theme.AppBackground
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.DeepPurple
import id.kotoba.app.view.theme.PastelCoral
import id.kotoba.app.view.theme.PastelGreen
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.SoftPurple
import id.kotoba.app.view.theme.SoftYellow
import id.kotoba.app.view.theme.TextPrimary
import id.kotoba.app.view.theme.TextSecondary
import id.kotoba.app.view.theme.WarmYellow

@Composable
fun GoalQuizScreen(controller: GoalQuizController) {
    val currentStep by controller.currentStep.collectAsState()
    val selectedGoal by controller.selectedGoal.collectAsState()
    val selectedKnowledge by controller.selectedKnowledge.collectAsState()
    val selectedDailyMinutes by controller.selectedDailyMinutes.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 640.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header & Progress Indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentStep > 0) {
                    Text(
                        text = "← Kembali",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { controller.prevStep() }
                            .padding(8.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.width(60.dp))
                }

                Text(
                    text = if (currentStep < 3) "Langkah ${currentStep + 1} dari 3" else "Hasil Penempatan",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryGreen
                )

                Spacer(modifier = Modifier.width(60.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar
            LinearProgressIndicator(
                progress = {
                    when (currentStep) {
                        0 -> 0.33f
                        1 -> 0.66f
                        2 -> 0.90f
                        else -> 1f
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = PrimaryGreen,
                trackColor = BorderOutline
            )

            Spacer(modifier = Modifier.height(28.dp))

            AnimatedContent(
                targetState = currentStep,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "GoalQuizStepTransition"
            ) { step ->
                when (step) {
                    0 -> StepPrimaryGoal(
                        selected = selectedGoal,
                        onSelect = { controller.selectGoal(it) },
                        onNext = { controller.nextStep() }
                    )

                    1 -> StepCurrentKnowledge(
                        selected = selectedKnowledge,
                        onSelect = { controller.selectKnowledge(it) },
                        onNext = { controller.nextStep() }
                    )

                    2 -> StepDailyTarget(
                        selected = selectedDailyMinutes,
                        onSelect = { controller.selectDailyMinutes(it) },
                        onNext = { controller.nextStep() }
                    )

                    3 -> StepSummaryPlacement(
                        goal = selectedGoal,
                        knowledge = selectedKnowledge,
                        dailyMinutes = selectedDailyMinutes,
                        onFinish = { controller.finishQuiz() }
                    )
                }
            }
        }

        if (currentStep == 3) {
            ConfettiView(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun StepPrimaryGoal(
    selected: PrimaryGoal,
    onSelect: (PrimaryGoal) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Apa Tujuan Utama Belajarmu? 🎯",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Kami akan menyesuaikan jalur materi dan contoh kontekstual agar belajarmu tidak membosankan.",
            fontSize = 14.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        GoalOptionCard(
            title = "Ngerti Anime & Pop-Culture",
            subtitle = "Paham dialog anime dan meme tanpa subtitle",
            badge = "📺 Anime Otaku",
            badgeColor = SoftPurple,
            badgeTextColor = DeepPurple,
            isSelected = selected == PrimaryGoal.ANIME_COMPREHENSION,
            onClick = { onSelect(PrimaryGoal.ANIME_COMPREHENSION) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        GoalOptionCard(
            title = "Lulus Sertifikasi JLPT N5",
            subtitle = "Kuasai Kanji dan tata bahasa terstruktur",
            badge = "📜 Pemburu Sertifikat",
            badgeColor = SoftYellow,
            badgeTextColor = WarmYellow,
            isSelected = selected == PrimaryGoal.JLPT_N5,
            onClick = { onSelect(PrimaryGoal.JLPT_N5) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        GoalOptionCard(
            title = "Ngobrol Santai & Traveling",
            subtitle = "Lancar percakapan di konbini dan jalan-jalan",
            badge = "✈️ Traveler & Obrolan",
            badgeColor = PastelGreen,
            badgeTextColor = PrimaryGreen,
            isSelected = selected == PrimaryGoal.CONVERSATION,
            onClick = { onSelect(PrimaryGoal.CONVERSATION) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
        ) {
            Text("Lanjut ke Level Pengetahuan →", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun StepCurrentKnowledge(
    selected: CurrentKnowledge,
    onSelect: (CurrentKnowledge) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Seberapa Jauh Kamu Kenal Huruf Jepang? 🎌",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Biar kamu tidak dipaksa mengulang materi yang sudah kamu kuasai dari nol!",
            fontSize = 14.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        GoalOptionCard(
            title = "Nol Besar (Pemula Mutlak)",
            subtitle = "Belum kenal huruf sama sekali. Mulai dari vokal dasar (Level 1).",
            badge = "🌱 Mulai Level 1",
            badgeColor = PastelCoral,
            badgeTextColor = Color(0xFFBE123C),
            isSelected = selected == CurrentKnowledge.ZERO,
            onClick = { onSelect(CurrentKnowledge.ZERO) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        GoalOptionCard(
            title = "Hafal Sebagian Hiragana",
            subtitle = "Sudah tahu bunyi vokal dasar. Mulai dari Dakuon (Level 2).",
            badge = "⚡ Lompat ke Level 2",
            badgeColor = SoftYellow,
            badgeTextColor = WarmYellow,
            isSelected = selected == CurrentKnowledge.SOME_KANA,
            onClick = { onSelect(CurrentKnowledge.SOME_KANA) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        GoalOptionCard(
            title = "Sudah Hafal Kana Lengkap",
            subtitle = "Sudah lancar Hiragana & Katakana. Langsung tancap gas ke Kosakata & Kanji!",
            badge = "🔥 Mulai Level 4",
            badgeColor = PastelGreen,
            badgeTextColor = PrimaryGreen,
            isSelected = selected == CurrentKnowledge.READY_FOR_KANJI,
            onClick = { onSelect(CurrentKnowledge.READY_FOR_KANJI) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
        ) {
            Text("Lanjut ke Target Waktu →", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun StepDailyTarget(
    selected: Int,
    onSelect: (Int) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Berapa Lama Mau Belajar per Hari? ⏱️",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Kunci menguasai bahasa adalah konsistensi harian lewat Spaced Repetition.",
            fontSize = 14.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        GoalOptionCard(
            title = "5 Menit per Hari",
            subtitle = "Santai & ringan di sela-sela waktu luang.",
            badge = "☕ Santai",
            badgeColor = SoftPurple,
            badgeTextColor = DeepPurple,
            isSelected = selected == 5,
            onClick = { onSelect(5) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        GoalOptionCard(
            title = "15 Menit per Hari",
            subtitle = "Standar emas! Kecepatan ideal untuk menghafal Kana & Kanji.",
            badge = "⭐ Rekomendasi",
            badgeColor = PastelGreen,
            badgeTextColor = PrimaryGreen,
            isSelected = selected == 15,
            onClick = { onSelect(15) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        GoalOptionCard(
            title = "30+ Menit per Hari",
            subtitle = "Mode akselerasi intensif bagi calon peserta JLPT.",
            badge = "🏆 Ambisius",
            badgeColor = SoftYellow,
            badgeTextColor = WarmYellow,
            isSelected = selected == 30,
            onClick = { onSelect(30) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
        ) {
            Text("Lihat Hasil Jalur Belajarmu ✨", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun StepSummaryPlacement(
    goal: PrimaryGoal,
    knowledge: CurrentKnowledge,
    dailyMinutes: Int,
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(PrimarySurface),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🎉", fontSize = 36.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Rencana Belajarmu Siap!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Berdasarkan jawabanmu, sistem telah menyiapkan kurikulum personal & jadwal SRS harian.",
            fontSize = 14.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Summary Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = PrimarySurface.copy(alpha = 0.5f),
            border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryGreen.copy(alpha = 0.2f))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                SummaryRow(label = "Tujuan Utama", value = goal.title)
                Spacer(modifier = Modifier.height(12.dp))
                SummaryRow(
                    label = "Titik Masuk Kurikulum",
                    value = when (knowledge) {
                        CurrentKnowledge.ZERO -> "Level 1: Hiragana Inti"
                        CurrentKnowledge.SOME_KANA -> "Level 2: Hiragana Dakuon (Skip Lvl 1)"
                        CurrentKnowledge.READY_FOR_KANJI -> "Level 4: Kosakata Anime (Skip Kana)"
                    },
                    highlight = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                SummaryRow(label = "Target Harian", value = "$dailyMinutes Menit / Hari")
                Spacer(modifier = Modifier.height(12.dp))
                SummaryRow(label = "Sistem Retensi", value = "SRS SM-2 Otomatis Aktif")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onFinish,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
        ) {
            Text("Masuk ke Beranda & Mulai Belajar 🚀", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    highlight: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = TextSecondary
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = if (highlight) FontWeight.Bold else FontWeight.SemiBold,
            color = if (highlight) PrimaryGreen else TextPrimary
        )
    }
}

@Composable
private fun GoalOptionCard(
    title: String,
    subtitle: String,
    badge: String,
    badgeColor: Color,
    badgeTextColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) PrimaryGreen else BorderOutline
    val borderWidth = if (isSelected) 2.dp else 1.dp
    val bgColor = if (isSelected) PrimarySurface.copy(alpha = 0.4f) else Color.White

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .border(borderWidth, borderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(badgeColor)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = badge,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = TextSecondary,
                lineHeight = 18.sp
            )
        }
    }
}
