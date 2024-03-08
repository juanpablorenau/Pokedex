package com.example.data.model.api

import com.example.model.PokemonInfo
import com.google.gson.annotations.SerializedName

data class PokemonInfoApiModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
)

fun PokemonInfoApiModel.toDomainModel() =
    PokemonInfo(
        id = id,
        name = name
    )
