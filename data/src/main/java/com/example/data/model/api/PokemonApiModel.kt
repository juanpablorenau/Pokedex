package com.example.data.model.api

import com.example.model.entities.Pokemon
import com.google.gson.annotations.SerializedName

data class PokemonApiModel(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
) {

    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/other/official-artwork/$index.png"
    }
}

fun PokemonApiModel.toDomainModel() = Pokemon(
    name = name,
    url = getImageUrl()
)
