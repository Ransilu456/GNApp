package com.emarketing_paradice.gnsrilanka.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4FC3F7), // A nice light blue
    onPrimary = Color.Black,
    primaryContainer = Color(0xFFB3E5FC),
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF81D4FA),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFE1F5FE),
    onSecondaryContainer = Color.Black,
    tertiary = Color(0xFF29B6F6),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFFB3E5FC),
    onTertiaryContainer = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFF0F0F0), // Light gray for card backgrounds, etc.
    onSurfaceVariant = Color.Black,
    outline = Color(0xFFBDBDBD), // For borders
    error = Color(0xFFB00020),
    onError = Color.White
)

@Composable
fun GNAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // For now, we'll only support light theme as requested.
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
