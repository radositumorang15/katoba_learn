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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import id.kotoba.app.controller.ProfileController
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.DeepPurple
import id.kotoba.app.view.theme.PastelBlue
import id.kotoba.app.view.theme.PastelCoral
import id.kotoba.app.view.theme.PastelGreen
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.ShapeBadgePill
import id.kotoba.app.view.theme.ShapeCardMedium
import id.kotoba.app.view.theme.ShapeHeroLarge
import id.kotoba.app.view.theme.SoftPurple
import id.kotoba.app.view.theme.SoftYellow
import id.kotoba.app.view.theme.SurfaceCard
import id.kotoba.app.view.theme.TextPrimary
import id.kotoba.app.view.theme.TextSecondary

@Composable
fun ProfileScreen(
    controller: ProfileController,
    modifier: Modifier = Modifier
) {
    val user by controller.currentUser.collectAsState()
    val masteredIds by controller.masteredCount.collectAsState()
    val isNormalSpeed by controller.isAudioSpeedNormal.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Profil Pembelajar",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Profile Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(ShapeHeroLarge)
                .background(PrimarySurface)
                .border(1.dp, PrimaryGreen.copy(alpha = 0.2f), ShapeHeroLarge)
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(2.dp, PrimaryGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user?.avatarLetter ?: "K",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                Column {
                    Text(
                        text = user?.displayName ?: "Otaku Learner",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = user?.email ?: "otaku@kotoba.app",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(ShapeBadgePill)
                            .background(Color.White)
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "🎌 ${user?.titleBadge ?: "Anime Otaku Explorer"}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryGreen
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Stats Grid 2x2
        Text(
            text = "Statistik Belajar",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Total XP",
                value = "${user?.totalXp ?: 0}",
                icon = "⚡",
                bg = SoftPurple,
                valueColor = DeepPurple,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Streak Harian",
                value = "${user?.currentStreak ?: 1} Hari",
                icon = "🔥",
                bg = PastelCoral,
                valueColor = Color(0xFFDC2626),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Koin Otaku",
                value = "${user?.coins ?: 0}",
                icon = "🪙",
                bg = SoftYellow,
                valueColor = Color(0xFFB45309),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Kana Dikuasai",
                value = "${masteredIds.size} Karakter",
                icon = "🎴",
                bg = PastelGreen,
                valueColor = PrimaryGreen,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Settings Section
        Text(
            text = "Pengaturan & Audio",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Audio speed toggle
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(ShapeCardMedium)
                .background(SurfaceCard)
                .border(1.dp, BorderOutline, ShapeCardMedium)
                .clickable { controller.toggleAudioSpeed() }
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Kecepatan Audio TTS",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (isNormalSpeed) "Normal (1.0x)" else "Pelan untuk Pemula (0.75x)",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(ShapeBadgePill)
                        .background(PastelBlue)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (isNormalSpeed) "1.0x" else "0.75x",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Sandbox Antigravity badge
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(ShapeCardMedium)
                .background(Color(0xFFF8FAFC))
                .border(1.dp, BorderOutline, ShapeCardMedium)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "🚀 Antigravity Core Engine Sandbox",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryGreen
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Aplikasi ini memvalidasi Compose Multiplatform, arsitektur MVC, audio-first native interop, dan cloud-sync.",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Reset progress & Logout
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(ShapeBadgePill)
                    .border(1.dp, BorderOutline, ShapeBadgePill)
                    .clickable { controller.resetProgress() }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Reset Progres",
                    color = Color(0xFFDC2626),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(ShapeBadgePill)
                    .background(Color(0xFFF1F5F9))
                    .clickable { controller.logout() }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Keluar",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: String,
    bg: Color,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(ShapeCardMedium)
            .background(SurfaceCard)
            .border(1.dp, BorderOutline, ShapeCardMedium)
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(bg),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.size(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 11.sp,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = valueColor
                )
            }
        }
    }
}
