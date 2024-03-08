package com.example.data.source.remote.api

import com.example.data.model.api.PokemonInfoApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokedexApi {

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: String): Call<PokemonInfoApiModel>
}