package com.example.data.source.local.impl

import com.example.data.model.room.MoveDbModel
import com.example.data.model.room.MoveInfoDbModel
import com.example.data.model.room.PokemonBasicInfoDbModel
import com.example.data.model.room.PokemonDbModel
import com.example.data.model.room.PokemonInfoDbModel
import com.example.data.model.room.PokemonTypeDbModel
import com.example.data.model.room.StatDbModel
import com.example.data.source.local.PokedexLocalDataSource
import com.example.data.source.local.dao.PokedexDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokedexLocalDataSourceImpl @Inject constructor(
    private val pokedexDao: PokedexDao,
    private val dispatcher: CoroutineDispatcher,
) : PokedexLocalDataSource {

    override suspend fun getPokemons(): List<PokemonDbModel> =
        withContext(dispatcher) { pokedexDao.getPokemons() }

    override suspend fun getPokemon(name: String): PokemonDbModel? =
        withContext(dispatcher) { pokedexDao.getPokemon(name) }

    override suspend fun insertPokemons(pokemons: List<PokemonDbModel>) {
        withContext(dispatcher) { pokedexDao.insertPokemons(pokemons) }
    }

    override suspend fun getPokemonInfo(name: String): PokemonInfoDbModel? =
        withContext(dispatcher) { pokedexDao.getPokemonInfo(name) }

    override suspend fun insertPokemonInfo(pokemonInfo: PokemonInfoDbModel) {
        withContext(dispatcher) {
            insertPokemonBasicInfo(pokemonInfo.pokemon)
            insertPokemonTypes(pokemonInfo.types)
            insertStats(pokemonInfo.stats)
            insertMoves(pokemonInfo.moves)
        }
    }

    override suspend fun insertPokemonBasicInfo(pokemonBasicInfoDbModel: PokemonBasicInfoDbModel) {
        withContext(dispatcher) { pokedexDao.insertPokemonBasicInfo(pokemonBasicInfoDbModel) }
    }

    override suspend fun insertPokemonTypes(pokemonTypes: List<PokemonTypeDbModel>) {
        withContext(dispatcher) { pokedexDao.insertPokemonTypes(pokemonTypes) }
    }

    override suspend fun insertMoves(moves: List<MoveDbModel>) {
        withContext(dispatcher) { pokedexDao.insertMoves(moves) }
    }

    override suspend fun insertStats(stats: List<StatDbModel>) {
        withContext(dispatcher) { pokedexDao.insertStats(stats) }
    }

    override suspend fun getMoveInfo(name: String): MoveInfoDbModel? =
        withContext(dispatcher) { pokedexDao.getMoveInfo(name) }

    override suspend fun insertMoveInfo(move: MoveInfoDbModel) {
        withContext(dispatcher) { pokedexDao.insertMoveInfo(move) }
    }
}