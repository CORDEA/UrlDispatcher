package jp.cordea.urldispatcher.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

// TODO(theme): swap FontFamily.SansSerif → Manrope and FontFamily.Monospace →
// JetBrains Mono once the font source (bundled TTF vs Downloadable Google
// Fonts) is settled. Sizing/weights below match the design mock.
val DisplayFamily: FontFamily = FontFamily.SansSerif
val MonoFamily: FontFamily = FontFamily.Monospace

val Typography = Typography(
        displayLarge = TextStyle(
                fontFamily = DisplayFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                lineHeight = 42.sp,
                letterSpacing = (-0.03).em
        ),
        displaySmall = TextStyle(
                fontFamily = DisplayFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                lineHeight = 33.sp,
                letterSpacing = (-0.025).em
        ),
        titleLarge = TextStyle(
                fontFamily = DisplayFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
                lineHeight = 24.sp,
                letterSpacing = (-0.015).em
        ),
        titleMedium = TextStyle(
                fontFamily = DisplayFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                lineHeight = 20.sp
        ),
        bodyLarge = TextStyle(
                fontFamily = DisplayFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                lineHeight = 21.sp
        ),
        bodyMedium = TextStyle(
                fontFamily = DisplayFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp
        ),
        labelLarge = TextStyle(
                fontFamily = DisplayFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
        ),
        labelMedium = TextStyle(
                fontFamily = DisplayFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
        ),
        labelSmall = TextStyle(
                fontFamily = DisplayFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
        )
)

// Mock-only styles that don't map cleanly onto Material 3's Typography scale.
val MonoLabelMedium = TextStyle(
        fontFamily = MonoFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        letterSpacing = 0.12.em
)

val MonoLabelSmall = TextStyle(
        fontFamily = MonoFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        letterSpacing = 0.09.em
)

val MonoBody = TextStyle(
        fontFamily = MonoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 21.sp
)

val MonoMeta = TextStyle(
        fontFamily = MonoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
)
