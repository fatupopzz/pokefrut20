package com.example.pokefrut20.data.repository

import com.example.pokefrut20.data.model.PokemonBasic
import com.example.pokefrut20.data.model.PokemonDetail
import com.example.pokefrut20.data.network.PokemonApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository que maneja el acceso a datos de Pokémon
 */
class PokemonRepository(
    private val apiService: PokemonApiService
) {

    /**
     * Obtiene la lista de Pokémon desde la API
     * @return Lista de Pokémon o lista vacía en caso de error
     */
    suspend fun getPokemonList(): Result<List<PokemonBasic>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPokemonList()
                Result.success(response.results)
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }
    }

    /**
     * Obtiene los detalles de un Pokémon específico
     * @param id ID del Pokémon
     * @return Detalles del Pokémon o null en caso de error
     */
    suspend fun getPokemonDetail(id: Int): Result<PokemonDetail> {
        return withContext(Dispatchers.IO) {
            try {
                val pokemonDetail = apiService.getPokemonDetail(id)
                Result.success(pokemonDetail)
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }
    }

    /**
     * Valida si un ID de Pokémon es válido
     * @param id ID a validar
     * @return true si el ID es válido
     */
    private fun isValidPokemonId(id: Int): Boolean {
        return id in 1..1010 // Rango actual de Pokémon en la API
    }
}