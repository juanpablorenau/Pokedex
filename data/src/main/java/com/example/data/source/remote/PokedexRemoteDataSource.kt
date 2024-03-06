package com.example.data.source.remote

import com.example.data.model.api.PokemonInfoApiModel
import kotlinx.coroutines.flow.Flow

interface PokedexRemoteDataSource {

    fun getPokemonInfo(name: String): Flow<PokemonInfoApiModel>
}