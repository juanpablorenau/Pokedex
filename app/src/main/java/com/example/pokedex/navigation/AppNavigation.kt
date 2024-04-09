package com.example.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.model.utils.orEmptyString
import com.example.pokedex.ui.info.PokemonInfoScreen
import com.example.pokedex.ui.list.PokemonsScreen
import com.example.pokedex.ui.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = AppScreens.SplashScreen.route
    ) {
        composable(route = AppScreens.SplashScreen.route) { SplashScreen(navController) }

        composable(route = AppScreens.PokemonsScreen.route) { PokemonsScreen(navController) }

        composable(
            route = AppScreens.PokemonInfoScreen.route + "/{name}",
            arguments = listOf(
                navArgument(name = "name") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                }
            )
        ) {
            val name = it.arguments?.getString("name").orEmptyString()
            PokemonInfoScreen(navController, name)
        }
    }
}
