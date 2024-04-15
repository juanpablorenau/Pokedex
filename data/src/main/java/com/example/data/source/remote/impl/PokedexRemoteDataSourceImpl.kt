package com.example.data.source.remote.impl

import com.example.data.model.api.ApiResponse
import com.example.data.model.api.CharacteristicsApiModel
import com.example.data.model.api.PokemonApiModel
import com.example.data.model.api.PokemonInfoApiModel
import com.example.data.source.remote.PokedexRemoteDataSource
import com.example.data.source.remote.api.PokedexApi
import com.example.data.utils.apiHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokedexRemoteDataSourceImpl @Inject constructor(
    private val api: PokedexApi,
    private val dispatcher: CoroutineDispatcher,
) : PokedexRemoteDataSource {

    override suspend fun getPokemons(page: Int): ApiResponse<PokemonApiModel> =
        withContext(dispatcher) { apiHandler { api.getPokemons(limit = page) } }

    override suspend fun getPokemonInfo(name: String): PokemonInfoApiModel =
        withContext(dispatcher) { apiHandler { api.getPokemonInfo(name) } }

    override suspend fun getPokemonCharacteristics(id: Int): CharacteristicsApiModel =
        withContext(dispatcher) { apiHandler { api.getPokemonCharacteristics(id) } }
}