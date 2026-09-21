package id.kotoba.app.view.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KotobaLightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    primaryContainer = PrimarySurface,
    onPrimaryContainer = PrimaryGreen,
    secondary = DeepPurple,
    onSecondary = Color.White,
    secondaryContainer = SoftPurple,
    onSecondaryContainer = DeepPurple,
    tertiary = WarmYellow,
    onTertiary = Color.White,
    tertiaryContainer = SoftYellow,
    onTertiaryContainer = TextPrimary,
    background = AppBackground,
    onBackground = TextPrimary,
    surface = SurfaceCard,
    onSurface = TextPrimary,
    surfaceVariant = PrimarySurface,
    onSurfaceVariant = TextSecondary,
    outline = BorderOutline,
    error = ErrorRed,
    onError = Color.White
)

@Composable
fun KotobaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = KotobaLightColorScheme,
        shapes = KotobaShapes,
        typography = KotobaTypography,
        content = content
    )
}
