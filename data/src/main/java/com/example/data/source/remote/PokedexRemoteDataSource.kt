package com.example.data.source.remote

import com.example.data.model.api.PokemonInfoApiModel
import com.example.data.source.remote.api.PokedexApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokedexRemoteDataSource(private val api: PokedexApi) {

    fun getPokemon(name: String): Flow<PokemonInfoApiModel> =
        flow {
            with(api.getPokemonInfo(name).execute()) {
                if (isSuccessful) body()?.let { emit(it) }
                else throw RuntimeException("Error: ${message()}")
            }
        }
}

