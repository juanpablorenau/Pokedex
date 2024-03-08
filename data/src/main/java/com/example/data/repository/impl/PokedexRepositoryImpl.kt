package com.example.data.repository.impl

import com.example.data.model.api.toDomainModel
import com.example.data.repository.PokedexRepository
import com.example.data.source.remote.PokedexRemoteDataSource
import com.example.model.PokemonInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokedexRepositoryImpl(
    private val remoteDataSource: PokedexRemoteDataSource,
) : PokedexRepository {

    override fun getPokemonInfo(name: String): Flow<PokemonInfo> =
        remoteDataSource.getPokemonInfo(name).map { it.toDomainModel() }
}