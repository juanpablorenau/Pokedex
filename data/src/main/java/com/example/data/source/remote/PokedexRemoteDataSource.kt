package com.example.data.source.remote

import com.example.data.model.api.ApiResponse
import com.example.data.model.api.PokemonApiModel
import com.example.data.model.api.PokemonInfoApiModel

interface PokedexRemoteDataSource {

    suspend fun getPokemons(): ApiResponse<PokemonApiModel>
    suspend fun getPokemonInfo(name: String): PokemonInfoApiModel
}