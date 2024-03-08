package com.example.data.source.remote.impl

import com.example.data.model.api.PokemonInfoApiModel
import com.example.data.source.remote.PokedexRemoteDataSource
import com.example.data.source.remote.api.PokedexApi
import com.example.data.utils.apiHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PokedexRemoteDataSourceImpl(
    private val api: PokedexApi,
    private val dispatcher: CoroutineDispatcher,
) : PokedexRemoteDataSource {

    override suspend fun getPokemonInfo(name: String): PokemonInfoApiModel =
        withContext(dispatcher) { apiHandler { api.getPokemonInfo(name) } }
}