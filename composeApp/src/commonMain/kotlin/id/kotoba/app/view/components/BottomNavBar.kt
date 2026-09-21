package id.kotoba.app.view.components

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kotoba.app.controller.BottomTab
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.TextSecondary

@Composable
fun BottomNavBar(
    selectedTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(width = 1.dp, color = BorderOutline)
            .padding(vertical = 10.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                icon = "🏠",
                title = "Home",
                isSelected = selectedTab == BottomTab.HOME,
                onClick = { onTabSelected(BottomTab.HOME) }
            )
            NavItem(
                icon = "🗺️",
                title = "Roadmap",
                isSelected = selectedTab == BottomTab.ROADMAP,
                onClick = { onTabSelected(BottomTab.ROADMAP) }
            )
            NavItem(
                icon = "🎴",
                title = "Flashcard",
                isSelected = selectedTab == BottomTab.LEARN,
                onClick = { onTabSelected(BottomTab.LEARN) }
            )
            NavItem(
                icon = "👤",
                title = "Profil",
                isSelected = selectedTab == BottomTab.PROFILE,
                onClick = { onTabSelected(BottomTab.PROFILE) }
            )
        }
    }
}

@Composable
private fun NavItem(
    icon: String,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val textColor by animateColorAsState(
        targetValue = if (isSelected) PrimaryGreen else TextSecondary
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (isSelected) PrimarySurface else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = textColor
        )
    }
}
