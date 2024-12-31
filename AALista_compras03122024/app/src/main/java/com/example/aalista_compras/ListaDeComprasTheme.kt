package com.example.aalista_compras.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1565C0), // Azul
    secondary = Color(0xFF00ACC1), // Azul claro
    background = Color(0xFF121212), // Fundo escuro
    surface = Color(0xFF1E1E1E), // Superfícies escuras
    onPrimary = Color.White, // Cor do texto sobre o primário
    onSecondary = Color.Black, // Cor do texto sobre o secundário
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2196F3), // Azul
    secondary = Color(0xFF4CAF50), // Verde claro
    background = Color.White, // Fundo claro
    surface = Color(0xFFF5F5F5), // Superfícies claras
    onPrimary = Color.White, // Cor do texto sobre o primário
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun ListaDeComprasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
