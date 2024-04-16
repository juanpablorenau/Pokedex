package com.example.data.source.remote.api

import com.example.data.model.api.ApiResponse
import com.example.data.model.api.CharacteristicsApiModel
import com.example.data.model.api.MoveInfoApiModel
import com.example.data.model.api.PokemonApiModel
import com.example.data.model.api.PokemonInfoApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int = 0,
        @Query("offset") offset: Int = 0,
    ): Response<ApiResponse<PokemonApiModel>>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String,
    ): Response<PokemonInfoApiModel>

    @GET("characteristic/{id}")
    suspend fun getPokemonCharacteristics(
        @Path("id") id: Int,
    ): Response<CharacteristicsApiModel>

    @GET("move/{name}")
    suspend fun getMoveInfo(
        @Path("name") name: String,
    ): Response<MoveInfoApiModel>
}