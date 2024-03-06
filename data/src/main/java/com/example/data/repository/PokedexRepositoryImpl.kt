package com.example.data.repository

import com.example.data.model.api.toDomainModel
import com.example.data.source.remote.PokedexRemoteDataSource
import com.example.domain.model.entities.PokemonInfo
import com.example.domain.repository.PokedexRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokedexRepositoryImpl(
    private val remoteDataSource: PokedexRemoteDataSource
) : PokedexRepository {
    override fun getPokemonInfo(name: String): Flow<PokemonInfo> =
        remoteDataSource.getPokemon(name).map { it.toDomainModel() }
}