package id.kotoba.app.view.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kotoba.app.controller.BottomTab
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.TextSecondary

import androidx.compose.foundation.layout.widthIn
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import id.kotoba.app.view.navigation.GlossaryTab
import id.kotoba.app.view.navigation.HomeTab
import id.kotoba.app.view.navigation.LearnTab
import id.kotoba.app.view.navigation.ProfileTab
import id.kotoba.app.view.navigation.RoadmapTab

@Composable
fun OptimizedBottomNavBar(modifier: Modifier = Modifier) {
    val tabNavigator = LocalTabNavigator.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(width = 1.dp, color = BorderOutline),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .widthIn(max = 760.dp)
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TabNavigationItem(tab = HomeTab, icon = "🏠")
            TabNavigationItem(tab = RoadmapTab, icon = "🗺️")
            TabNavigationItem(tab = LearnTab, icon = "🎴")
            TabNavigationItem(tab = GlossaryTab, icon = "🤖")
            TabNavigationItem(tab = ProfileTab, icon = "👤")
        }
    }
}

@Composable
private fun TabNavigationItem(tab: Tab, icon: String) {
    val tabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current == tab

    val textColor by animateColorAsState(
        targetValue = if (isSelected) PrimaryGreen else TextSecondary
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = 500f)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { tabNavigator.current = tab }
            .padding(horizontal = 8.dp, vertical = 4.dp),
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
            text = tab.options.title,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = textColor
        )
    }
}

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
            .border(width = 1.dp, color = BorderOutline),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .widthIn(max = 760.dp)
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp),
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
                icon = "🤖",
                title = "Sensei",
                isSelected = selectedTab == BottomTab.GLOSSARY,
                onClick = { onTabSelected(BottomTab.GLOSSARY) }
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
            .padding(horizontal = 8.dp, vertical = 4.dp),
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
