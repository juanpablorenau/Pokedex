package com.example.data.repository.impl

import com.example.data.model.api.toDomainModel
import com.example.data.model.room.toDbModel
import com.example.data.model.room.toDomainModel
import com.example.data.repository.PokemonRepository
import com.example.data.source.local.PokedexLocalDataSource
import com.example.data.source.remote.PokedexRemoteDataSource
import com.example.model.entities.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokedexRemoteDataSource,
    private val localDataSource: PokedexLocalDataSource,
    private val dispatcher: CoroutineDispatcher,
) : PokemonRepository {

    override fun getPokemons(): Flow<List<Pokemon>> = flow {
        val localPokemons = localDataSource.getPokemons().map { it.toDomainModel() }
        if (localPokemons.isEmpty()) {
            var page = 0
            do {
                val remotePokemons =
                    remoteDataSource.getPokemons(page++).results.map { it.toDomainModel() }
                        .also { pokemons ->
                            localDataSource.insertPokemons(pokemons.map { it.toDbModel() })
                            emit(pokemons)
                        }
            } while (remotePokemons.isNotEmpty())
        } else {
            emit(localPokemons)
        }
    }.flowOn(dispatcher)

    override fun getPokemon(name: String): Flow<Pokemon> = flow {
        localDataSource.getPokemon(name)?.let { pokemon -> emit(pokemon.toDomainModel()) }
            ?: throw Exception("Pokemon $name no encontrado")
    }.flowOn(dispatcher)
}