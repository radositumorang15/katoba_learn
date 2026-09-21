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
import androidx.compose.foundation.layout.size
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
import id.kotoba.app.controller.ActionMenu
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.PastelBlue
import id.kotoba.app.view.theme.PastelCoral
import id.kotoba.app.view.theme.PastelGreen
import id.kotoba.app.view.theme.ShapeCardMedium
import id.kotoba.app.view.theme.SoftPurple
import id.kotoba.app.view.theme.SurfaceCard
import id.kotoba.app.view.theme.TextPrimary
import id.kotoba.app.view.theme.TextSecondary

@Composable
fun ActionMenuGrid(
    onMenuClick: (ActionMenu) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
    ) {
        // Row 1
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            ActionCard(
                title = "All Classes",
                subtitle = "6 Level Lengkap",
                iconText = "⛩️",
                iconBg = PastelGreen,
                modifier = Modifier.weight(1f),
                onClick = { onMenuClick(ActionMenu.ALL_CLASSES) }
            )
            ActionCard(
                title = "My Courses",
                subtitle = "Hiragana Seion",
                iconText = "📖",
                iconBg = PastelBlue,
                modifier = Modifier.weight(1f),
                onClick = { onMenuClick(ActionMenu.MY_COURSES) }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Row 2
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            ActionCard(
                title = "My Shelf",
                subtitle = "Catatan & Flashcard",
                iconText = "🔖",
                iconBg = SoftPurple,
                modifier = Modifier.weight(1f),
                onClick = { onMenuClick(ActionMenu.MY_SHELF) }
            )
            ActionCard(
                title = "My Progress",
                subtitle = "Statistik & XP",
                iconText = "📊",
                iconBg = PastelCoral,
                modifier = Modifier.weight(1f),
                onClick = { onMenuClick(ActionMenu.MY_PROGRESS) }
            )
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    subtitle: String,
    iconText: String,
    iconBg: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(ShapeCardMedium)
            .background(SurfaceCard)
            .border(1.dp, BorderOutline, ShapeCardMedium)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = iconText,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }
    }
}
