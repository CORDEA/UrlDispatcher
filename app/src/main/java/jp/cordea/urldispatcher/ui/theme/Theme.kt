package jp.cordea.urldispatcher.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

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
        outlineVariant = StoneLight,
        inverseSurface = Ink,
        inverseOnSurface = Sand
)

@Immutable
data class ExtendedColors(
        val inkMuted: Color,
        val stoneMid: Color,
        val chevron: Color
)

private val LightExtendedColors = ExtendedColors(
        inkMuted = InkMuted,
        stoneMid = StoneMid,
        chevron = Chevron
)

val LocalExtendedColors = staticCompositionLocalOf {
        ExtendedColors(
                inkMuted = Color.Unspecified,
                stoneMid = Color.Unspecified,
                chevron = Color.Unspecified
        )
}

object AppTheme {
        val extended: ExtendedColors
                @Composable
                @ReadOnlyComposable
                get() = LocalExtendedColors.current
}

@Composable
fun UrlDispatcherTheme(content: @Composable () -> Unit) {
    // Dark mode is intentionally out of scope for v1; the design mock is
    // light-only. Always use the light color scheme regardless of system
    // setting.
    CompositionLocalProvider(LocalExtendedColors provides LightExtendedColors) {
        MaterialTheme(
                colorScheme = LightColors,
                typography = Typography,
                shapes = Shapes,
                content = content
        )
    }
}
