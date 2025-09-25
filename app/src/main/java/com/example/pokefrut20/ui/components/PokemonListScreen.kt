package com.example.pokefrut20.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.pokefrut20.data.model.PokemonBasic
import com.example.pokefrut20.ui.viewmodel.PokemonListViewModel

/**
 * Pantalla principal que muestra la lista de Pokémon
 * Implementa Clean Code: funciones pequeñas, nombres descriptivos
 *
 * @param viewModel ViewModel que maneja el estado
 * @param onPokemonClick Callback cuando se hace click en un Pokémon
 * @param modifier Modificador de Compose
 */
@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    onPokemonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                LoadingIndicator()
            }
            uiState.hasError -> {
                ErrorState(
                    message = uiState.errorMessage ?: "Error desconocido",
                    onRetry = viewModel::retryLoading
                )
            }
            uiState.isEmpty -> {
                EmptyState(onRetry = viewModel::retryLoading)
            }
            else -> {
                PokemonList(
                    pokemonList = uiState.pokemonList,
                    onPokemonClick = onPokemonClick
                )
            }
        }
    }
}

/**
 * Lista scrolleable de Pokémon
 * Componente reutilizable y testeable
 */
@Composable
private fun PokemonList(
    pokemonList: List<PokemonBasic>,
    onPokemonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = pokemonList,
            key = { pokemon -> pokemon.id }
        ) { pokemon ->
            PokemonListItem(
                pokemon = pokemon,
                onClick = { onPokemonClick(pokemon.id) }
            )
        }
    }
}

/**
 * Item individual de la lista de Pokémon
 * Siguiendo Material Design 3 guidelines
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonListItem(
    pokemon: PokemonBasic,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PokemonImage(
                imageUrl = pokemon.imageUrl,
                pokemonName = pokemon.name,
                size = 80.dp
            )

            PokemonBasicInfo(pokemon = pokemon)
        }
    }
}

/**
 * Imagen del Pokémon con manejo de estados de carga
 */
@Composable
private fun PokemonImage(
    imageUrl: String,
    pokemonName: String,
    size: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.size(size),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Imagen de $pokemonName",
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentScale = ContentScale.Fit
        )
    }
}

/**
 * Información básica del Pokémon en la lista
 */
@Composable
private fun PokemonBasicInfo(
    pokemon: PokemonBasic,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = pokemon.name.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "ID: #${pokemon.id.toString().padStart(3, '0')}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}