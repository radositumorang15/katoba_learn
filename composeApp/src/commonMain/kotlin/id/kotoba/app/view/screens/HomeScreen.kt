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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import id.kotoba.app.controller.HomeController
import id.kotoba.app.controller.LibraryTab
import id.kotoba.app.view.components.ActionMenuGrid
import id.kotoba.app.view.components.CourseCard
import id.kotoba.app.view.components.HeaderBar
import id.kotoba.app.view.components.HeroBannerCard
import id.kotoba.app.view.components.SegmentedPillTabs
import id.kotoba.app.view.theme.ShapeBadgePill
import id.kotoba.app.view.theme.SoftYellow
import id.kotoba.app.view.theme.TextPrimary
import id.kotoba.app.view.theme.TextSecondary

@Composable
fun HomeScreen(
    controller: HomeController,
    modifier: Modifier = Modifier
) {
    val user by controller.currentUser.collectAsState()
    val levels by controller.levels.collectAsState()
    val selectedTab by controller.selectedLibraryTab.collectAsState()
    val noticeMessage by controller.selectedMenuNotice.collectAsState()

    val filteredLevels = when (selectedTab) {
        LibraryTab.ALL_LIBRARY -> levels
        LibraryTab.COMPLETED -> levels.filter { it.isCompleted }
    }

    val activeLevel = levels.firstOrNull { it.isUnlocked && !it.isCompleted }
        ?: levels.firstOrNull()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header Bar
            item {
                HeaderBar(
                    user = user,
                    onAvatarClick = { controller.onActionMenuClick(id.kotoba.app.controller.ActionMenu.MY_PROGRESS) },
                    onNotificationClick = { /* Show notice */ }
                )
            }

            // Hero Banner Card ("AI Sensei")
            item {
                HeroBannerCard(
                    currentLevelTitle = activeLevel?.title ?: "Level 1: Hiragana Inti",
                    streakDays = user?.currentStreak ?: 4,
                    onStartClick = {
                        activeLevel?.let { controller.startLearning(it.id) }
                    }
                )
            }

            // Kotak Ulangan (Review Box - SRS Spaced Repetition) - PRD 4 FASE 3
            item {
                val dueCount by controller.dueReviewCount.collectAsState()
                val masteredCount by controller.masteredCount.collectAsState()

                ReviewBoxCard(
                    dueCount = dueCount,
                    masteredCount = masteredCount,
                    onStartReview = { controller.startDailyReview() },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }

            // Action Notice Snackbar if triggered
            if (noticeMessage != null) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 6.dp)
                            .clip(ShapeBadgePill)
                            .background(SoftYellow)
                            .clickable { controller.dismissNotice() }
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = noticeMessage ?: "",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                            Text(
                                text = "✕",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }

            // 2x2 Action Menu Grid
            item {
                ActionMenuGrid(
                    onMenuClick = { controller.onActionMenuClick(it) }
                )
            }

            // Section Title
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Kurikulum Kana & Anime",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "${levels.count { it.isCompleted }}/${levels.size} Selesai",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0F755E)
                    )
                }
            }

            // Segmented Pill Tabs ("All Library", "Completed")
            item {
                SegmentedPillTabs(
                    selectedTab = selectedTab,
                    onTabSelected = { controller.selectLibraryTab(it) }
                )
            }

            // Course Cards List
            if (filteredLevels.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Belum ada level yang diselesaikan.\nSelesaikan kuis untuk melihat di sini!",
                            fontSize = 13.sp,
                            color = Color(0xFF7A7E86),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            } else {
                items(filteredLevels) { level ->
                    CourseCard(
                        level = level,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        onStartLearning = { controller.startLearning(level.id) },
                        onStartQuiz = { controller.startQuiz(level.id) }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
private fun ReviewBoxCard(
    dueCount: Int,
    masteredCount: Int,
    onStartReview: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(18.dp)),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp),
        color = Color(0xFFF7FBF9),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFD1EAE3))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "📦", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Kotak Ulangan Harian (SRS)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(ShapeBadgePill)
                        .background(if (dueCount > 0) SoftYellow else Color(0xFFDCFCE7))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (dueCount > 0) "$dueCount Jatuh Tempo" else "✓ Bersih",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (dueCount > 0) Color(0xFFB45309) else Color(0xFF15803D)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (dueCount > 0) {
                    "Ada $dueCount item yang dijadwalkan ulang oleh sistem Spaced Repetition (SRS) agar tersimpan di memori jangka panjang."
                } else {
                    "Semua kartu telah diulang dengan baik! Kamu sudah menguasai $masteredCount item secara permanen."
                },
                fontSize = 13.sp,
                color = TextSecondary,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🏆 $masteredCount Item Dikuasai",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF0F755E)
                )

                androidx.compose.material3.Button(
                    onClick = onStartReview,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0F755E)
                    ),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = if (dueCount > 0) "Mulai Review ($dueCount) →" else "Latihan Bebas →",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
