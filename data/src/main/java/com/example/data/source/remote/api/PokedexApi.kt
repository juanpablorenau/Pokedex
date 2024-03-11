package com.example.data.source.remote.api

import com.example.data.model.api.PokemonApiModel
import com.example.data.model.api.PokemonInfoApiModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokedexApi {

    @GET("pokemon}")
    suspend fun getPokemons(): Response<List<PokemonApiModel>>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: String): Response<PokemonInfoApiModel>
}