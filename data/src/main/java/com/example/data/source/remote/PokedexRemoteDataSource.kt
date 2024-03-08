package com.example.data.source.remote

import com.example.data.model.api.PokemonInfoApiModel
import kotlinx.coroutines.flow.Flow

interface PokedexRemoteDataSource {

    suspend fun getPokemonInfo(name: String): PokemonInfoApiModel
}