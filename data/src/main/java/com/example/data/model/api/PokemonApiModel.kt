package com.example.data.model.api

import com.example.model.entities.Pokemon
import com.google.gson.annotations.SerializedName

data class PokemonApiModel(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)

fun PokemonApiModel.toDomainModel() = Pokemon(
    name = name,
    url = url
)
