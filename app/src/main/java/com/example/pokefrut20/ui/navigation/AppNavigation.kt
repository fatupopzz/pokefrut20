package com.example.pokefrut20.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokefrut20.data.network.NetworkModule
import com.example.pokefrut20.data.repository.PokemonRepository
import com.example.pokefrut20.navigation.NavigationRoutes
import com.example.pokefrut20.ui.components.PokemonDetailScreen
import com.example.pokefrut20.ui.components.PokemonListScreen
import com.example.pokefrut20.ui.viewmodel.PokemonDetailViewModel
import com.example.pokefrut20.ui.viewmodel.PokemonListViewModel

/**
 * Configuración principal de navegación de la aplicación
 * Implementa patrón de navegación limpio con inyección de dependencias manual
 *
 * @param navController Controlador de navegación
 * @param modifier Modificador de Compose
 */
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    // Crear instancia del repository (en una app real usaríamos DI como Hilt)
    val repository = createPokemonRepository()

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.POKEMON_LIST,
        modifier = modifier
    ) {
        // Pantalla de lista de Pokémon (pantalla principal)
        composable(route = NavigationRoutes.POKEMON_LIST) {
            val viewModel = createPokemonListViewModel(repository)

            PokemonListScreen(
                viewModel = viewModel,
                onPokemonClick = { pokemonId ->
                    navController.navigate(
                        NavigationRoutes.createPokemonDetailRoute(pokemonId)
                    )
                }
            )
        }

        // Pantalla de detalles del Pokémon
        composable(
            route = NavigationRoutes.POKEMON_DETAIL,
            arguments = listOf(
                navArgument(NavigationRoutes.POKEMON_ID_ARG) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val pokemonIdString = backStackEntry.arguments?.getInt(NavigationRoutes.POKEMON_ID_ARG)?.toString()
            val pokemonId = NavigationRoutes.extractPokemonId(pokemonIdString)
            val viewModel = createPokemonDetailViewModel(repository)

            PokemonDetailScreen(
                pokemonId = pokemonId,
                viewModel = viewModel
            )
        }
    }
}

/**
 * Factory function para crear el repository
 * En una app real esto estaría en un módulo de DI
 */
@Composable
private fun createPokemonRepository(): PokemonRepository {
    return PokemonRepository(NetworkModule.pokemonApiService)
}

/**
 * Factory function para crear el ViewModel de la lista
 * En una app real usaríamos @HiltViewModel o ViewModelProvider
 */
@Composable
private fun createPokemonListViewModel(repository: PokemonRepository): PokemonListViewModel {
    return androidx.lifecycle.viewmodel.compose.viewModel {
        PokemonListViewModel(repository)
    }
}

/**
 * Factory function para crear el ViewModel de detalles
 * En una app real usaríamos @HiltViewModel o ViewModelProvider
 */
@Composable
private fun createPokemonDetailViewModel(repository: PokemonRepository): PokemonDetailViewModel {
    return androidx.lifecycle.viewmodel.compose.viewModel {
        PokemonDetailViewModel(repository)
    }
}