package com.example.data.repository.impl

import com.example.data.model.api.toDomainModel
import com.example.data.repository.PokedexRepository
import com.example.data.source.remote.PokedexRemoteDataSource
import com.example.model.PokemonInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PokedexRepositoryImpl(
    private val remoteDataSource: PokedexRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) : PokedexRepository {

    override fun getPokemonInfo(name: String): Flow<PokemonInfo> = flow {
        remoteDataSource.getPokemonInfo(name).also { emit(it.toDomainModel()) }
    }.flowOn(dispatcher)
}