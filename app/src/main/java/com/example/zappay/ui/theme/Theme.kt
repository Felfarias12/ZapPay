package com.example.zappay.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BrightBlue,           // Botones y elementos principales
    secondary = BrightGreen,        // Acentos o indicadores
    tertiary = PinkAccent,          // Color auxiliar
    background = Black,             // Fondo general de la app
    surface = White,           // Tarjetas, diálogos, barras
    onPrimary = White,              // Texto en botones
    onSecondary = White,           // Texto en chips o acentos
    onTertiary = White,
    onBackground = Color.White,           // Texto principal sobre fondo negro
    onSurface = White               // Texto sobre tarjetas

)


private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
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