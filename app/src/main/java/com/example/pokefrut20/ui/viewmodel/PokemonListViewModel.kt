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
 * Maneja el estado de la UI siguiendo patrón MVVM
 */
class PokemonListViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    // Estado privado mutable
    private val _uiState = MutableStateFlow(PokemonListUiState())

    // Estado público inmutable
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    init {
        loadPokemonList()
    }

    /**
     * Carga la lista de Pokémon desde el repository
     */
    fun loadPokemonList() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            repository.getPokemonList()
                .onSuccess { pokemonList ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        pokemonList = pokemonList,
                        errorMessage = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = getErrorMessage(exception)
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
     * Limpiar mensaje de error
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    /**
     * Convierte excepciones en mensajes de error amigables
     */
    private fun getErrorMessage(exception: Throwable): String {
        return when {
            exception.message?.contains("network", ignoreCase = true) == true ->
                "Error de conexión. Revisa tu internet."
            exception.message?.contains("timeout", ignoreCase = true) == true ->
                "La conexión tardó demasiado. Inténtalo de nuevo."
            else -> "Error al cargar los Pokémon. Inténtalo de nuevo."
        }
    }
}

/**
 * Estado de la UI para la lista de Pokémon
 */
data class PokemonListUiState(
    val isLoading: Boolean = false,
    val pokemonList: List<PokemonBasic> = emptyList(),
    val errorMessage: String? = null
) {
    val isEmpty: Boolean
        get() = pokemonList.isEmpty() && !isLoading && errorMessage == null

    val hasError: Boolean
        get() = errorMessage != null
}