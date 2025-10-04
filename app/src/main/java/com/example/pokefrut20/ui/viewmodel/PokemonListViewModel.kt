package com.example.pokefrut20.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokefrut20.data.model.PokemonBasic
import com.example.pokefrut20.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de lista de Pokémon
 * Implementa MVVM exponiendo el estado mediante StateFlow
 */
class PokemonListViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    // Estado privado mutable
    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)

    // Estado público inmutable expuesto a la UI
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    init {
        loadPokemonList()
    }

    /**
     * Carga la lista de Pokémon desde el repository
     * Maneja los estados: Loading, Success, Error
     */
    fun loadPokemonList() {
        viewModelScope.launch {
            _uiState.value = PokemonListUiState.Loading

            repository.getPokemonList()
                .onSuccess { pokemonList ->
                    _uiState.value = if (pokemonList.isEmpty()) {
                        PokemonListUiState.Empty
                    } else {
                        PokemonListUiState.Success(pokemonList)
                    }
                }
                .onFailure { exception ->
                    _uiState.value = PokemonListUiState.Error(
                        message = getErrorMessage(exception)
                    )
                }
        }
    }

    /**
     * Reintentar la carga de datos
     */
    fun retryLoading() {
        loadPokemonList()
    }

    /**
     * Convierte excepciones en mensajes de error amigables para el usuario
     */
    private fun getErrorMessage(exception: Throwable): String {
        return when {
            exception.message?.contains("network", ignoreCase = true) == true ->
                "Error de conexión. Revisa tu internet."
            exception.message?.contains("timeout", ignoreCase = true) == true ->
                "La conexión tardó demasiado. Inténtalo de nuevo."
            exception.message?.contains("404", ignoreCase = true) == true ->
                "Recurso no encontrado."
            else -> "Error al cargar los Pokémon: ${exception.message ?: "Error desconocido"}"
        }
    }
}

/**
 * Estados posibles de la UI para la lista de Pokémon
 * Sealed class para representar todos los estados de forma exhaustiva
 */
sealed class PokemonListUiState {
    data object Loading : PokemonListUiState()
    data class Success(val pokemonList: List<PokemonBasic>) : PokemonListUiState()
    data class Error(val message: String) : PokemonListUiState()
    data object Empty : PokemonListUiState()
}