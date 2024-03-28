package com.example.pokedex.navigation

sealed class AppScreens(val route: String) {
    data object PokemonsScreen : AppScreens("pokemons_screen")
    data object PokemonInfoScreen : AppScreens("pokemon_info_screen")
}
