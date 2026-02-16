package com.emarketing_paradice.gnsrilanka.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColorScheme =
        lightColorScheme(
                primary = PrimaryBlue,
                onPrimary = Color(0xFFFFFFFF),
                primaryContainer = PrimaryVariant,
                onPrimaryContainer = Color.White,
                secondary = SecondaryGold,
                onSecondary = Color.Black,
                secondaryContainer = Color(0xFFFFE082),
                onSecondaryContainer = Color.Black,
                tertiary = PrimaryBlue,
                onTertiary = Color.White,
                background = BackgroundLight,
                onBackground = TextPrimary,
                surface = Color.White,
                onSurface = TextPrimary,
                surfaceVariant = Color(0xFFEEEEEE),
                onSurfaceVariant = TextSecondary,
                outline = Color(0xFFBDBDBD),
                error = ErrorRed,
                onError = Color.White
        )

private val DarkColorScheme =
        darkColorScheme(
                primary = Color(0xFF90CAF9),
                onPrimary = Color(0xFF0D47A1),
                primaryContainer = Color(0xFF1565C0),
                onPrimaryContainer = Color(0xFFE3F2FD),
                secondary = Color(0xFFFFCC80),
                onSecondary = Color(0xFFE65100),
                secondaryContainer = Color(0xFFFFB300),
                onSecondaryContainer = Color(0xFFFFF3E0),
                background = Color(0xFF121212),
                onBackground = Color(0xFFE1E1E1),
                surface = Color.Black,
                onSurface = Color(0xFFE1E1E1),
                surfaceVariant = Color(0xFF2C2C2C),
                onSurfaceVariant = Color(0xFFBDBDBD),
                outline = Color(0xFF424242),
                error = Color(0xFFCF6679),
                onError = Color.Black
        )

val AppShapes =
        Shapes(
                small = RoundedCornerShape(12.dp),
                medium = RoundedCornerShape(20.dp),
                large = RoundedCornerShape(30.dp)
        )

@Composable
fun GNAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
        val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

        MaterialTheme(
                colorScheme = colorScheme,
                typography = MaterialTheme.typography,
                shapes = AppShapes,
                content = content
        )
}
