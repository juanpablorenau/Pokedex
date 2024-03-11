package com.example.data.repository.impl

import com.example.data.model.api.toDomainModel
import com.example.data.repository.PokemonRepository
import com.example.data.source.remote.PokedexRemoteDataSource
import com.example.model.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PokemonRepositoryImpl(
    private val remoteDataSource: PokedexRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) : PokemonRepository {
    override fun getPokemons(): Flow<List<Pokemon>> = flow {
        emit(remoteDataSource.getPokemons().map { it.toDomainModel() })
    }.flowOn(dispatcher)
}