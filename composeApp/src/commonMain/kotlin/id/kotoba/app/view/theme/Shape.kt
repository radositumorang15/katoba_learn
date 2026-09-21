package id.kotoba.app.view.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Corner Radii Tokens (From design.md)
val ShapeExtraLarge = RoundedCornerShape(28.dp)
val ShapeHeroLarge = RoundedCornerShape(24.dp)
val ShapeCardMedium = RoundedCornerShape(20.dp)
val ShapeImageSmall = RoundedCornerShape(16.dp)
val ShapeBadgePill = CircleShape

val KotobaShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = ShapeCardMedium,
    large = ShapeHeroLarge,
    extraLarge = ShapeExtraLarge
)
