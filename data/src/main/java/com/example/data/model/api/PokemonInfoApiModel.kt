package com.example.data.model.api

import com.example.model.entities.PokemonInfo
import com.example.model.entities.enums.PokemonType
import com.google.gson.annotations.SerializedName

data class PokemonInfoApiModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("types") val types: List<TypeApiModel>,
) {

    fun getImageUrl() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
            "pokemon/other/official-artwork/$id.png"

    fun getPokemonTypes() = types.map {
        try {
            PokemonType.valueOf(it.type.name.uppercase())
        } catch (e: IllegalArgumentException) {
            PokemonType.UNKNOWN
        }
    }
}

fun PokemonInfoApiModel.toDomainModel() =
    PokemonInfo(
        id = id,
        name = name,
        weight = weight,
        url = getImageUrl(),
        types = getPokemonTypes()
    )
