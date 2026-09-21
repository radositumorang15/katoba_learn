package id.kotoba.app.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import id.kotoba.app.model.UserProfile
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.DeepPurple
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.ShapeBadgePill
import id.kotoba.app.view.theme.SoftPurple
import id.kotoba.app.view.theme.TextPrimary

@Composable
fun HeaderBar(
    user: UserProfile?,
    onAvatarClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: Avatar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onAvatarClick() }
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(PrimarySurface)
                    .border(2.dp, PrimaryGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user?.avatarLetter ?: "K",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryGreen
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Kon'nichiwa, ",
                    fontSize = 14.sp,
                    color = TextPrimary
                )
                Text(
                    text = user?.displayName ?: "Otaku",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryGreen
                )
            }
        }

        // Right: Coin/XP Pill Badge & Bell
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Coin / XP Pill Badge (soft purple background, deep purple text/icon)
            Box(
                modifier = Modifier
                    .clip(ShapeBadgePill)
                    .background(SoftPurple)
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⚡",
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${user?.totalXp ?: 0} XP",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepPurple
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "🪙 ${user?.coins ?: 0}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepPurple
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Notification Bell Outline
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, BorderOutline, CircleShape)
                    .clickable { onNotificationClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🔔",
                    fontSize = 16.sp
                )
            }
        }
    }
}
