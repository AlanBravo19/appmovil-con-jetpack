package com.example.appmovil.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography as Material3Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ==== COLORES VERDES PRINCIPALES ====
val GreenPrimary = Color(0xFF2E7D32)      // Verde fuerte
val GreenSecondary = Color(0xFF4CAF50)    // Verde suave
val GreenAccent = Color(0xFF81C784)       // Verde claro

// ==== COLORES DE TEXTO (SIEMPRE VISIBLES) ====
val TextOnPrimary = Color.White
val TextOnBackground = Color(0xFF1B1B1B)

private val LightColors = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = TextOnPrimary,

    secondary = GreenSecondary,
    onSecondary = TextOnPrimary,

    tertiary = GreenAccent,
    onTertiary = TextOnBackground,

    background = Color(0xFFF8FFF8),
    onBackground = TextOnBackground,

    surface = Color.White,
    onSurface = TextOnBackground,

    error = Color(0xFFD32F2F),
    onError = Color.White
)

// Usa un Typography de Material3 vacío por defecto (sin choque de nombres)
private val AppTypography = Material3Typography()

@Composable
fun AppMovilTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        content = content
    )
}
