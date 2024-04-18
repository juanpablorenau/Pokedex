package com.example.data.source.local

import com.example.data.model.room.MoveDbModel
import com.example.data.model.room.MoveInfoDbModel
import com.example.data.model.room.PokemonBasicInfoDbModel
import com.example.data.model.room.PokemonDbModel
import com.example.data.model.room.PokemonInfoDbModel
import com.example.data.model.room.PokemonTypeDbModel
import com.example.data.model.room.StatDbModel

interface PokedexLocalDataSource {

    suspend fun getPokemons(): List<PokemonDbModel>
    suspend fun getPokemon(name: String): PokemonDbModel?
    suspend fun insertPokemons(pokemons: List<PokemonDbModel>)

    suspend fun getPokemonInfo(name: String): PokemonInfoDbModel?
    suspend fun insertPokemonInfo(pokemonInfo: PokemonInfoDbModel)

    suspend fun insertPokemonBasicInfo(pokemonBasicInfoDbModel: PokemonBasicInfoDbModel)
    suspend fun insertPokemonTypes(pokemonTypes: List<PokemonTypeDbModel>)
    suspend fun insertMoves(moves: List<MoveDbModel>)
    suspend fun insertStats(stats: List<StatDbModel>)

    suspend fun getMoveInfo(name: String): MoveInfoDbModel?
    suspend fun insertMoveInfo(move: MoveInfoDbModel)
}