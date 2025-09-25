package com.example.pokefrut20.data.model

/**
 * Respuesta de la API para la lista de Pokémon
 */
data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonBasic>
)

/**
 * Información básica de un Pokémon en la lista
 */
data class PokemonBasic(
    val name: String,
    val url: String
) {
    val id: Int
        get() = url.split("/").dropLast(1).last().toInt()

    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}

/**
 * Información detallada de un Pokémon
 */
data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: PokemonSprites
) {
    val formattedName: String
        get() = name.replaceFirstChar { it.uppercase() }

    val heightInMeters: String
        get() = "${height / 10.0} m"

    val weightInKg: String
        get() = "${weight / 10.0} kg"
}

/**
 * Sprites/imágenes del Pokémon
 */
data class PokemonSprites(
    val front_default: String?,
    val front_shiny: String?,
    val back_default: String?,
    val back_shiny: String?
)