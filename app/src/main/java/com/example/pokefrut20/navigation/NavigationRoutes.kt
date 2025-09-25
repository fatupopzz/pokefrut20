package com.example.pokefrut20.navigation

/**
 * Definición centralizada de rutas de navegación
 * Siguiendo principios Clean Code: nombres descriptivos y constantes bien definidas
 */
object NavigationRoutes {

    // Rutas principales
    const val POKEMON_LIST = "pokemon_list"
    const val POKEMON_DETAIL = "pokemon_detail/{pokemonId}"

    // Argumentos
    const val POKEMON_ID_ARG = "pokemonId"

    /**
     * Crea la ruta para navegar a los detalles de un Pokémon
     * @param pokemonId ID del Pokémon
     * @return Ruta formateada para navegación
     */
    fun createPokemonDetailRoute(pokemonId: Int): String {
        return "pokemon_detail/$pokemonId"
    }

    /**
     * Extrae el ID del Pokémon de los argumentos de navegación
     * @param pokemonId String del ID recibido
     * @return ID como entero o 1 por defecto
     */
    fun extractPokemonId(pokemonId: String?): Int {
        return pokemonId?.toIntOrNull() ?: 1
    }
}