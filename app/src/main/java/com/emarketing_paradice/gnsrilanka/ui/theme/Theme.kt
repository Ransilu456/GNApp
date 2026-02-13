package com.emarketing_paradice.gnsrilanka.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColorScheme =
        lightColorScheme(
                primary = PrimaryBlue,
                onPrimary = Color.White,
                primaryContainer = PrimaryVariant,
                onPrimaryContainer = Color.White,
                secondary = SecondaryGold,
                onSecondary = Color.Black,
                secondaryContainer = Color(0xFFFFE082),
                onSecondaryContainer = Color.Black,
                tertiary = PrimaryBlue, // Using primary as tertiary for consistency
                onTertiary = Color.White,
                background = AppBackground,
                onBackground = TextPrimary,
                surface = SurfaceWhite,
                onSurface = TextPrimary,
                surfaceVariant = Color(0xFFEEEEEE),
                onSurfaceVariant = TextSecondary,
                outline = Color(0xFFBDBDBD),
                error = ErrorRed,
                onError = Color.White
        )

val AppShapes =
        androidx.compose.material3.Shapes(
                small = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                medium = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
                large = androidx.compose.foundation.shape.RoundedCornerShape(30.dp)
        )

@Composable
fun GNAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    // For now, we'll only support light theme as requested.
    val colorScheme = LightColorScheme

    MaterialTheme(
            colorScheme = colorScheme,
            typography = MaterialTheme.typography,
            shapes = AppShapes,
            content = content
    )
}
