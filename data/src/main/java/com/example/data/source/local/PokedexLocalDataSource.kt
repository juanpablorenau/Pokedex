package com.example.data.source.local

import com.example.data.model.room.MoveInfoDbModel
import com.example.data.model.room.PokemonDbModel
import com.example.data.model.room.PokemonInfoDbModel

interface PokedexLocalDataSource {

    suspend fun getPokemons(): List<PokemonDbModel>
    suspend fun getPokemon(name: String): PokemonDbModel?
    suspend fun insertPokemons(pokemons: List<PokemonDbModel>)

    suspend fun getPokemonInfo(name: String): PokemonInfoDbModel?

    suspend fun getMoveInfo(name: String): MoveInfoDbModel?
    suspend fun insertMoveInfo(move: MoveInfoDbModel)
}