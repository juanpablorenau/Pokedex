package com.example.data.source.local.impl

import com.example.data.model.room.PokemonDbModel
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
}