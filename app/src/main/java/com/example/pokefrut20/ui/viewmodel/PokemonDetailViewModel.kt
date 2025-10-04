package com.example.pokefrut20.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokefrut20.data.model.PokemonDetail
import com.example.pokefrut20.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de detalles del Pokémon
 * Maneja el estado de un Pokémon específico
 */
class PokemonDetailViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    // Estado privado mutable
    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)

    // Estado público inmutable
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    /**
     * Carga los detalles de un Pokémon específico
     * @param pokemonId ID del Pokémon a cargar
     */
    fun loadPokemonDetail(pokemonId: Int) {
        viewModelScope.launch {
            _uiState.value = PokemonDetailUiState.Loading

            repository.getPokemonDetail(pokemonId)
                .onSuccess { pokemonDetail ->
                    _uiState.value = PokemonDetailUiState.Success(pokemonDetail)
                }
                .onFailure { exception ->
                    _uiState.value = PokemonDetailUiState.Error(
                        message = getErrorMessage(exception)
                    )
                }
        }
    }

    /**
     * Reintentar la carga de detalles
     */
    fun retryLoading(pokemonId: Int) {
        loadPokemonDetail(pokemonId)
    }

    /**
     * Convierte excepciones en mensajes de error amigables
     */
    private fun getErrorMessage(exception: Throwable): String {
        return when {
            exception.message?.contains("404", ignoreCase = true) == true ->
                "Pokémon no encontrado."
            exception.message?.contains("network", ignoreCase = true) == true ->
                "Error de conexión. Revisa tu internet."
            exception.message?.contains("timeout", ignoreCase = true) == true ->
                "La conexión tardó demasiado. Inténtalo de nuevo."
            else -> "Error al cargar el Pokémon: ${exception.message ?: "Error desconocido"}"
        }
    }
}

/**
 * Estados posibles de la UI para los detalles del Pokémon
 * Sealed class para representar todos los estados posibles
 */
sealed class PokemonDetailUiState {
    data object Loading : PokemonDetailUiState()
    data class Success(val pokemonDetail: PokemonDetail) : PokemonDetailUiState()
    data class Error(val message: String) : PokemonDetailUiState()
}