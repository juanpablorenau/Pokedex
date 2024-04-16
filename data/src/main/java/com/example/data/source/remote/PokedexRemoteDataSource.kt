package com.example.data.source.remote

import com.example.data.model.api.ApiResponse
import com.example.data.model.api.CharacteristicsApiModel
import com.example.data.model.api.MoveInfoApiModel
import com.example.data.model.api.PokemonApiModel
import com.example.data.model.api.PokemonInfoApiModel

interface PokedexRemoteDataSource {

    suspend fun getPokemons(page: Int): ApiResponse<PokemonApiModel>
    suspend fun getPokemonInfo(name: String): PokemonInfoApiModel
    suspend fun getPokemonCharacteristics(id: Int) : CharacteristicsApiModel
    suspend fun getMoveInfo(name: String) : MoveInfoApiModel
}