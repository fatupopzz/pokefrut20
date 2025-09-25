package com.example.pokefrut20.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta de colores Frutiger Aero
private val PokemonRed = Color(0xFF7FC8F8)        // Azul suave (era rojo)
private val PokemonBlue = Color(0xFF5AA9E6)       // Azul acuático
private val PokemonYellow = Color(0xFF9BE5AA)     // Verde menta (era amarillo)
private val PokemonGreen = Color(0xFF7ED3B2)      // Verde agua
private val PokemonPurple = Color(0xFF87CEEB)     // Azul cielo (era púrpura)

// Esquema de colores claro - Frutiger Aero
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF5AA9E6),                   // Azul acuático principal
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE1F5FE),          // Azul muy claro
    onPrimaryContainer = Color(0xFF0D47A1),        // Azul oscuro
    secondary = Color(0xFF87CEEB),                 // Azul cielo
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F6FF),        // Azul cristalino
    onSecondaryContainer = Color(0xFF001D36),
    tertiary = Color(0xFF9BE5AA),                  // Verde menta
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFE8F8EA),         // Verde muy claro
    onTertiaryContainer = Color(0xFF1B5E20),       // Verde oscuro
    error = Color(0xFF7FC8F8),                     // Azul suave en lugar de rojo
    errorContainer = Color(0xFFE1F5FE),
    onError = Color.White,
    onErrorContainer = Color(0xFF0D47A1),
    background = Color(0xFFFAFEFF),                // Blanco con tinte azul
    onBackground = Color(0xFF1A1C1E),
    surface = Color(0xFFFAFEFF),                   // Superficie con tinte azul
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFE1F5FE),            // Variante azul claro
    onSurfaceVariant = Color(0xFF42474E),
    outline = Color(0xFF87CEEB),                   // Contorno azul cielo
    inverseOnSurface = Color(0xFFF0F4F8),
    inverseSurface = Color(0xFF2F3239),
    inversePrimary = Color(0xFF7FC8F8),
)

// Esquema de colores oscuro - Frutiger Aero Nocturno
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF7FC8F8),                   // Azul suave brillante
    onPrimary = Color(0xFF0D47A1),
    primaryContainer = Color(0xFF1565C0),          // Azul medio
    onPrimaryContainer = Color(0xFFE1F5FE),
    secondary = Color(0xFF87CEEB),                 // Azul cielo
    onSecondary = Color(0xFF003258),
    secondaryContainer = Color(0xFF004B7C),        // Azul oscuro
    onSecondaryContainer = Color(0xFFE0F6FF),
    tertiary = Color(0xFF9BE5AA),                  // Verde menta brillante
    onTertiary = Color(0xFF1B5E20),
    tertiaryContainer = Color(0xFF2E7D32),         // Verde medio
    onTertiaryContainer = Color(0xFFE8F8EA),
    error = Color(0xFF7FC8F8),                     // Azul suave
    errorContainer = Color(0xFF1565C0),
    onError = Color(0xFF0D47A1),
    onErrorContainer = Color(0xFFE1F5FE),
    background = Color(0xFF0A1628),                // Azul muy oscuro
    onBackground = Color(0xFFE1F5FE),
    surface = Color(0xFF0A1628),                   // Superficie azul oscuro
    onSurface = Color(0xFFE1F5FE),
    surfaceVariant = Color(0xFF1565C0),            // Variante azul medio
    onSurfaceVariant = Color(0xFFBBE1FF),
    outline = Color(0xFF87CEEB),                   // Contorno azul cielo
    inverseOnSurface = Color(0xFF0A1628),
    inverseSurface = Color(0xFFE1F5FE),
    inversePrimary = Color(0xFF5AA9E6),
)

/**
 * Tema principal de la aplicación PokeFrut
 * Utiliza Material 3 Design System con colores personalizados
 *
 * @param darkTheme Si debe usar el tema oscuro
 * @param content Contenido a renderizar con el tema aplicado
 */
@Composable
fun PokeFrutTheme(
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
        typography = MaterialTheme.typography,
        content = content
    )
}