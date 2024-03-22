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
        val localPokemons = localDataSource.getPokemons()
        if (localPokemons.isEmpty()) {
            val remotePokemons = remoteDataSource.getPokemons().results
            localDataSource.insertPokemons(remotePokemons.map { it.toDomainModel().toDbModel() })
            emit(remotePokemons.map { it.toDomainModel() })
        } else {
            emit(localPokemons.map { it.toDomainModel() })
        }
    }.flowOn(dispatcher)
}