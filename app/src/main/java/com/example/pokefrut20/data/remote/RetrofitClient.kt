package com.example.pokefrut20.data.remote

import com.example.pokefrut20.data.model.PokemonDetail
import com.example.pokefrut20.data.model.PokemonListResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Cliente Retrofit configurado para consumir la PokéAPI
 * Singleton que proporciona una instancia única de Retrofit
 */
object RetrofitClient {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    private const val TIMEOUT_SECONDS = 30L

    /**
     * Interceptor para logging de peticiones HTTP
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * Cliente HTTP con configuración de timeouts y logging
     */
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    /**
     * Instancia de Retrofit configurada
     */
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Servicio API para operaciones con Pokémon
     */
    val apiService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}

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