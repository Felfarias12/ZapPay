package com.example.zappay.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Eliminamos los colores duplicados y solo dejamos estos:
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00C853),           // Verde principal
    secondary = Color(0xFF2979FF),         // Azul secundario
    tertiary = Color(0xFFD0BCFF),          // Morado terciario
    background = Color(0xFF121212),        // Fondo oscuro
    surface = Color(0xFF1E1E1E),           // Surface oscuro
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2D2D2D)     // Variante de surface
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00C853),           // Verde principal
    secondary = Color(0xFF2979FF),         // Azul secundario
    tertiary = Color(0xFF6650a4),          // Morado terciario
    background = Color(0xFFF5F5F5),        // Fondo claro
    surface = Color.White,                 // Surface claro
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFEEEEEE)     // Variante de surface
)

@Composable
fun ZapPayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}