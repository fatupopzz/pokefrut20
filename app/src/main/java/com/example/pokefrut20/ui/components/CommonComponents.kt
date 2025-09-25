package com.example.pokefrut20.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * Componentes comunes reutilizables siguiendo principios DRY
 * Cada componente tiene una responsabilidad específica
 */

/**
 * TopAppBar personalizada para la aplicación
 * Maneja navegación automática hacia atrás
 * Optimizada para fondo Frutiger Aero
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeFrutAppBar(
    title: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0x9A00BCD4).copy(alpha = 0.9f) // Semi-transparente
        ),
        navigationIcon = {
            // Solo mostrar botón de regreso si hay pantalla anterior
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Color.White
                    )
                }
            }
        },
        modifier = modifier
    )
}

/**
 * Indicador de carga centrado
 * Reutilizable en toda la aplicación
 */
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    message: String = "Cargando..."
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )

            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Estado de error con botón de reintentar
 * Maneja diferentes tipos de errores de forma consistente
 */
@Composable
fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    retryButtonText: String = "Reintentar"
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Ícono de error
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Error",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )

                // Mensaje de error
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )

                // Botón de reintentar
                Button(
                    onClick = onRetry,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = retryButtonText)
                }
            }
        }
    }
}

/**
 * Estado vacío cuando no hay datos para mostrar
 * Diferente del estado de error
 */
@Composable
fun EmptyState(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    message: String = "No se encontraron Pokémon",
    actionText: String = "Cargar Pokémon"
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Emoji de Pokéball
                Text(
                    text = "⚪",
                    style = MaterialTheme.typography.displayMedium
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                OutlinedButton(onClick = onRetry) {
                    Text(text = actionText)
                }
            }
        }
    }
}

/**
 * Divider personalizado para separar secciones
 */
@Composable
fun PokeFrutDivider(
    modifier: Modifier = Modifier,
    thickness: androidx.compose.ui.unit.Dp = 1.dp
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    )
}

/**
 * Espaciador vertical consistente
 */
@Composable
fun VerticalSpacer(
    height: androidx.compose.ui.unit.Dp = 16.dp
) {
    Spacer(modifier = Modifier.height(height))
}

/**
 * Espaciador horizontal consistente
 */
@Composable
fun HorizontalSpacer(
    width: androidx.compose.ui.unit.Dp = 16.dp
) {
    Spacer(modifier = Modifier.width(width))
}