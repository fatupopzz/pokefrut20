package com.example.pokefrut20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pokefrut20.navigation.NavigationRoutes
import com.example.pokefrut20.ui.components.FrutigerAeroBackground
import com.example.pokefrut20.ui.components.PokeFrutAppBar
import com.example.pokefrut20.ui.navigation.AppNavigation
import com.example.pokefrut20.ui.theme.PokeFrutTheme

/**
 * Activity principal de PokeFrut 2.0
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokeFrutTheme {
                PokeFrutApp()
            }
        }
    }
}

/**
 * Composable principal de la aplicación
 * Configura la estructura general con Scaffold, navegación y fondo Frutiger Aero
 */
@Composable
private fun PokeFrutApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Determinar el título basado en la ruta actual
    val screenTitle = determineScreenTitle(currentRoute)

    // Fondo Frutiger Aero con tu imagen personalizada
    FrutigerAeroBackground(
        backgroundAlpha = 0.8f // Ajusta la transparencia si es necesario
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent // Hacer el Surface transparente
        ) {
            Scaffold(
                containerColor = Color.Transparent, // Hacer el Scaffold transparente
                topBar = {
                    PokeFrutAppBar(
                        title = screenTitle,
                        navController = navController
                    )
                }
            ) { paddingValues ->
                AppNavigation(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

/**
 * Determina el título de la pantalla basado en la ruta actual
 */
private fun determineScreenTitle(currentRoute: String?): String {
    return when {
        currentRoute == NavigationRoutes.POKEMON_LIST -> "Pokedex"
        currentRoute?.startsWith("pokemon_detail") == true -> "Detalles del Pokémon"
        else -> "PokéFrut 2.0"
    }
}