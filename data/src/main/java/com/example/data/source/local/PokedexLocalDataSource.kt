package com.example.data.source.local

import com.example.data.model.room.PokemonDbModel

interface PokedexLocalDataSource {

    suspend fun getPokemons(): List<PokemonDbModel>
    suspend fun getPokemon(name: String): PokemonDbModel?
    suspend fun insertPokemons(pokemons: List<PokemonDbModel>)
}