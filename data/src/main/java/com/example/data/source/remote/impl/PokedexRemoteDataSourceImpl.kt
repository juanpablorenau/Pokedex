package com.example.data.source.remote.impl

import com.example.data.model.api.PokemonInfoApiModel
import com.example.data.source.remote.PokedexRemoteDataSource
import com.example.data.source.remote.api.PokedexApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokedexRemoteDataSourceImpl(private val api: PokedexApi) : PokedexRemoteDataSource {

    override fun getPokemonInfo(name: String): Flow<PokemonInfoApiModel> =
        flow {
            with(api.getPokemonInfo(name).execute()) {
                if (isSuccessful) body()?.let { emit(it) }
                else throw RuntimeException("Error: ${message()}")
            }
        }
}