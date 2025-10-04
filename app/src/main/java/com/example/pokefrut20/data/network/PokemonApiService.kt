package com.example.pokefrut20.data.network

import com.example.pokefrut20.data.model.PokemonDetail
import com.example.pokefrut20.data.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz para las llamadas a la PokéAPI
 */
interface PokemonApiService {

    /**
     * Obtiene la lista de Pokémon
     * @param limit Número máximo de Pokémon a obtener (por defecto 100)
     * @param offset Posición de inicio para paginación
     * @return Lista de Pokémon básicos
     */
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    /**
     * Obtiene los detalles de un Pokémon específico
     * @param id ID único del Pokémon
     * @return Detalles completos del Pokémon
     */
    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: Int): PokemonDetail
}