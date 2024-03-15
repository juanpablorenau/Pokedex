package com.example.data.source.remote.api

import com.example.data.model.api.PokemonApiModel
import com.example.data.model.api.PokemonInfoApiModel
import com.example.data.model.api.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokedexApi {

    @GET("pokemon")
    suspend fun getPokemons(): Response<ApiResponse<PokemonApiModel>>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: String): Response<PokemonInfoApiModel>
}