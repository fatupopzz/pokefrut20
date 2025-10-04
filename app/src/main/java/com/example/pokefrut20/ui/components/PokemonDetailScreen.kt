package com.example.pokefrut20.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.pokefrut20.data.model.PokemonDetail
import com.example.pokefrut20.ui.viewmodel.PokemonDetailUiState
import com.example.pokefrut20.ui.viewmodel.PokemonDetailViewModel

/**
 * Pantalla de detalles del Pokémon
 * Muestra las 4 imágenes requeridas y información adicional
 *
 * @param pokemonId ID del Pokémon a mostrar
 * @param viewModel ViewModel que maneja el estado
 * @param modifier Modificador de Compose
 */
@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    viewModel: PokemonDetailViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Cargar datos cuando cambia el ID
    LaunchedEffect(pokemonId) {
        viewModel.loadPokemonDetail(pokemonId)
    }

    Column(modifier = modifier.fillMaxSize()) {
        when (val state = uiState) {
            is PokemonDetailUiState.Loading -> {
                LoadingIndicator()
            }
            is PokemonDetailUiState.Error -> {
                ErrorState(
                    message = state.message,
                    onRetry = { viewModel.retryLoading(pokemonId) }
                )
            }
            is PokemonDetailUiState.Success -> {
                PokemonDetailContent(pokemon = state.pokemonDetail)
            }
        }
    }
}

/**
 * Contenido principal de los detalles del Pokémon
 * Layout scrolleable con toda la información
 */
@Composable
private fun PokemonDetailContent(
    pokemon: PokemonDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Título del Pokémon
        PokemonHeader(pokemon = pokemon)

        // Grid de 4 imágenes (requerimiento principal)
        PokemonSpritesGrid(pokemon = pokemon)

        // Información adicional
        PokemonInfoCard(pokemon = pokemon)
    }
}

/**
 * Header con el nombre y ID del Pokémon
 */
@Composable
private fun PokemonHeader(
    pokemon: PokemonDetail,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = pokemon.formattedName,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center
            )

            Text(
                text = "ID: #${pokemon.id.toString().padStart(3, '0')}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )
        }
    }
}

/**
 * Grid con las 4 imágenes del Pokémon según requerimientos
 * Front, Back, Front Shiny, Back Shiny
 */
@Composable
private fun PokemonSpritesGrid(
    pokemon: PokemonDetail,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título de la sección
            Text(
                text = "Sprites",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            // Fila superior: Normal sprites
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SpriteImageCard(
                    imageUrl = pokemon.sprites.front_default,
                    label = "Front",
                    modifier = Modifier.weight(1f)
                )
                SpriteImageCard(
                    imageUrl = pokemon.sprites.back_default,
                    label = "Back",
                    modifier = Modifier.weight(1f)
                )
            }

            // Fila inferior: Shiny sprites
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SpriteImageCard(
                    imageUrl = pokemon.sprites.front_shiny,
                    label = "Front Shiny",
                    modifier = Modifier.weight(1f)
                )
                SpriteImageCard(
                    imageUrl = pokemon.sprites.back_shiny,
                    label = "Back Shiny",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * Card individual para cada sprite del Pokémon
 */
@Composable
private fun SpriteImageCard(
    imageUrl: String?,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Label
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            // Imagen
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "$label sprite",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

/**
 * Card con información básica del Pokémon
 */
@Composable
private fun PokemonInfoCard(
    pokemon: PokemonDetail,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Información",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Divider(
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.2f),
                thickness = 1.dp
            )

            InfoRow(
                label = "Altura",
                value = pokemon.heightInMeters
            )

            InfoRow(
                label = "Peso",
                value = pokemon.weightInKg
            )
        }
    }
}

/**
 * Fila de información reutilizable
 */
@Composable
private fun InfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
        )
    }
}