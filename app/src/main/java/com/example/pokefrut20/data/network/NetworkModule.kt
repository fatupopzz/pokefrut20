package com.example.pokefrut20.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Módulo de configuración de red
 * Singleton para manejo eficiente de recursos de red
 */
object NetworkModule {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    private const val TIMEOUT_SECONDS = 30L

    /**
     * Interceptor para logging de requests/responses
     * Solo activo en modo debug para mejor performance en producción
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * Cliente HTTP configurado con timeouts y logging
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
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Servicio API para Pokémon
     * Lazy initialization para mejor performance
     */
    val pokemonApiService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}