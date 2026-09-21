package id.kotoba.app.view.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import id.kotoba.app.controller.LibraryTab
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.ShapeBadgePill
import id.kotoba.app.view.theme.TextSecondary

@Composable
fun SegmentedPillTabs(
    selectedTab: LibraryTab,
    onTabSelected: (LibraryTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clip(ShapeBadgePill)
            .background(PrimarySurface)
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TabButton(
                title = "All Library",
                isSelected = selectedTab == LibraryTab.ALL_LIBRARY,
                modifier = Modifier.weight(1f),
                onClick = { onTabSelected(LibraryTab.ALL_LIBRARY) }
            )
            TabButton(
                title = "Completed",
                isSelected = selectedTab == LibraryTab.COMPLETED,
                modifier = Modifier.weight(1f),
                onClick = { onTabSelected(LibraryTab.COMPLETED) }
            )
        }
    }
}

@Composable
private fun TabButton(
    title: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) PrimaryGreen else Color.Transparent
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else TextSecondary
    )

    Box(
        modifier = modifier
            .clip(ShapeBadgePill)
            .background(bgColor)
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = textColor,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}
