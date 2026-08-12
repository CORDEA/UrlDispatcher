package jp.cordea.urldispatcher.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
        primary = Indigo,
        onPrimary = Card,
        primaryContainer = IndigoSurface,
        onPrimaryContainer = Indigo,
        secondary = Ink,
        onSecondary = Sand,
        background = Bone,
        onBackground = Ink,
        surface = Sand,
        onSurface = Ink,
        surfaceVariant = StoneLight,
        onSurfaceVariant = InkSubtle,
        surfaceContainer = Card,
        surfaceContainerHigh = Card,
        outline = Stone,
        outlineVariant = StoneLight
)

@Composable
fun UrlDispatcherTheme(content: @Composable () -> Unit) {
    // Dark mode is intentionally out of scope for v1; the design mock is
    // light-only. Always use the light color scheme regardless of system
    // setting.
    MaterialTheme(
            colorScheme = LightColors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}
